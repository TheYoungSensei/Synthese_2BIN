package be.ipl.csacre15.jemanager.businessDays;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import be.ipl.csacre15.jemanager.HTTPUtils;

/**
 * Created by sacre on 14-06-17.
 */
public class BusinessModel {

    private Set<BusinessDayObserver> businessDayObservers = new HashSet<BusinessDayObserver>();
    private List<String> companies = new ArrayList<String>();
    private Set<CompaniesObserver> companiesObservers = new HashSet<CompaniesObserver>();

    public void registerBusinessDayObserver(BusinessDayObserver obs) {
        this.businessDayObservers.add(obs);
    }

    public void unregisterBusinessDayObserver(BusinessDayObserver obs) {
        this.businessDayObservers.remove(obs);
    }


    public interface BusinessDayObserver {
        public void notifyChange();
    }

    private void notifyAllBusinessDayObservers() {
        for(BusinessDayObserver obs : this.businessDayObservers) {
            obs.notifyChange();
        }
    }

    public void registerCompaniesObserver(CompaniesObserver obs) {
        this.companiesObservers.add(obs);
    }

    public void unregisterCompaniesObserver(CompaniesObserver obs) {
        this.companiesObservers.remove(obs);
    }


    public interface CompaniesObserver {
        public void notifyChange();
    }

    private void notifyAllCompaniesObservers() {
        for(CompaniesObserver obs : this.companiesObservers) {
            obs.notifyChange();
        }
    }

    public BusinessDayTask newBusinessDayTask() {
        return new BusinessDayTask();
    }

    public CompaniesTask newCompaniesTask(BusinessDayContent.BusinessDayItem mItem) {
        return new CompaniesTask(mItem);
    }

    public List<String> getCompanies() {
        return Collections.unmodifiableList(this.companies);
    }

    public class BusinessDayTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                Map<String, String> map = new HashMap<String, String>();
                map.put("type", "showEvents");
                return HTTPUtils.performPostCall(HTTPUtils.URL, map);
            } catch (HTTPUtils.HTTPException exception) {
                return null;
            } catch (HTTPUtils.HTTPNetworkException exception ) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s == null)
                return;
            try {
                JSONArray events = new JSONArray(s);
                for(int i = 0; i < events.length(); i++) {
                    JSONObject event = events.getJSONObject(i);
                    String date = event.getJSONObject("date").getInt("dayOfMonth") + "-" + event.getJSONObject("date").getInt("monthValue") + "-" + event.getJSONObject("date").getInt("year");
                    BusinessDayContent.addItem(new BusinessDayContent.BusinessDayItem(String.valueOf(i + 1), date, event.getInt("eventId")));
                }
                notifyAllBusinessDayObservers();
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
        }
    }

    public class CompaniesTask extends AsyncTask<String, Void, String> {

        private BusinessDayContent.BusinessDayItem mItem;

        public CompaniesTask(BusinessDayContent.BusinessDayItem mItem) {
            this.mItem = mItem;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Map<String, String> map = new HashMap<String, String>();
                map.put("json", String.valueOf(mItem.details));
                map.put("type", "showConfirmedEventParticipations");
                return HTTPUtils.performPostCall(HTTPUtils.URL, map);
            } catch (HTTPUtils.HTTPException exception) {
                return null;
            } catch (HTTPUtils.HTTPNetworkException exception ) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s == null)
                return;
            try {
                companies = new ArrayList<>();
                JSONArray participations = new JSONArray(s);
                for(int i = 0; i < participations.length(); i++) {
                    JSONObject participation = participations.getJSONObject(i);
                    companies.add(participation.getJSONObject("company").getString("name"));
                }
                notifyAllCompaniesObservers();
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
        }

    }

}
