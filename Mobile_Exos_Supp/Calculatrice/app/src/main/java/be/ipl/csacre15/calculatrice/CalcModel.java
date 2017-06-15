package be.ipl.csacre15.calculatrice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csacre15 on 15/03/2017.
 */
public class CalcModel {
    private boolean operandLeftActive = true, operandLeftDisplay = true;
    private char operator;
    private String operandLeft = "0", operandRight = "";

    public void setArtihmeticException(boolean artihmeticException) {
        this.artihmeticException = artihmeticException;
    }

    public boolean isArtihmeticException() {

        return artihmeticException;
    }

    private boolean artihmeticException;

    private List<CalcModelChangeObserver> observers = new ArrayList<>();
    private boolean autoGenerate;

    public void registerObserver(CalcModelChangeObserver obs) {
        observers.add(obs);
    }

    public void unregister(CalcModelChangeObserver obs) {
        observers.remove(obs);
    }

    private void notifyAllObservers(){
        for(CalcModelChangeObserver obs : observers) {
            obs.notifyChange();
        }
    }

    private void notifyArithmeticObservers(){
        this.artihmeticException = true;
        for(CalcModelChangeObserver obs : observers) {
            obs.notifyArithmeticException();
        }
    }


    public void addDigit(char digit) {
        operandLeftDisplay = operandLeftActive;
        if(isOperandLeftActive()) {
            if(operandLeft.equals("0"))
                operandLeft = "";
            if(this.operandLeft.contains(".") && autoGenerate) {
                if (this.operandLeft.charAt(operandLeft.length() - 1) == '0') {
                    this.operandLeft = this.operandLeft.substring(0, this.operandLeft.length() - 1);
                }
            }
            operandLeft += digit;
        } else {
            if(this.operandRight.contains(".") && autoGenerate) {
                if (this.operandRight.charAt(operandRight.length() - 1) == '0') {
                    this.operandRight = this.operandRight.substring(0, this.operandRight.length() - 1);
                }
            }
            operandRight += digit;
        }
        autoGenerate = false;
        notifyAllObservers();
    }

    public void addOperator(char operator) {
        if(operandLeftActive) {
            this.operator = operator;
            operandLeftActive = false;
            operandRight = "";
        } else {
            if(!operandRight.equals("")) {
                calc();
                operandLeftDisplay = true;
                operandRight = "";
            }
        }
        notifyAllObservers();
    }

    public void operation(char operation) {
        switch(operation) {
            case 'D': //DELETE
                if(operandLeftActive) {
                    operandLeft = "0";
                } else {
                    operandRight = "";
                }
                break;
            case 'C': //CLEAR
                this.operator = '\u0000';
                operandRight = "";
                operandLeft = "0";
                operandLeftActive = true;
                operandLeftDisplay = true;
                break;
            case 'I': //INVERSE
                if(operandLeftActive) {
                    operandLeft = String.valueOf(- Double.parseDouble(operandLeft));
                } else {
                    if(!operandRight.equals("")) {
                        operandRight = String.valueOf((-Double.parseDouble(operandRight)));
                    }
                }
                break;
            case 'S' ://MOINS UN CARAC
                if(operandLeftActive) {
                    if(!operandLeft.equals(""))
                        this.operandLeft = this.operandLeft.substring(0, this.operandLeft.length()- 1);
                } else {
                    if(operandLeftDisplay) {
                        this.operator = '\u0000';
                        this.operandLeftActive = true;
                        this.operandLeftDisplay = true;
                    } else {
                        if(!operandRight.equals(""))
                            this.operandRight = this.operandRight.substring(0, this.operandRight.length() - 1);
                    }
                }
                break;
            case '=':
                if(!operandRight.equals("")) {
                    calc();
                    operandLeftDisplay = true;
                    operandRight = "";
                }
                break;
            case ',':
                if(operandLeftActive){
                    if(!this.operandLeft.contains(".")) {
                        this.operandLeft += ".";
                    } else {
                        if(this.operandLeft.charAt(operandLeft.length() - 1) == '0'){
                            this.operandLeft = this.operandLeft.substring(0, this.operandLeft.length()- 1);
                        }
                    }
                } else {
                    if(!this.operandRight.contains(".")) {
                        this.operandRight += ".";
                    }else {
                            if(this.operandRight.charAt(operandRight.length() - 1) == '0'){
                                this.operandRight = this.operandRight.substring(0, this.operandRight.length() - 1);
                            }
                        }
                }

        }
        notifyAllObservers();
    }

    private void calc() {
        Double res;
        switch(operator) {
            case '+':
                res = Double.parseDouble(operandLeft) + Double.parseDouble(operandRight);
                break;
            case '-':
                res = Double.parseDouble(operandLeft) - Double.parseDouble(operandRight);
                break;
            case 'X':
                res = Double.parseDouble(operandLeft) * Double.parseDouble(operandRight);
                break;
            case '/':
                if(Double.parseDouble(operandRight) == 0) {
                    notifyArithmeticObservers();
                    return;
                }
                res = Double.parseDouble(operandLeft) / Double.parseDouble(operandRight);
                break;
            default:
                return;
        }
        autoGenerate = true;
        //operator = '\u0000';
        operandLeftActive = true;
        operandLeft = res.toString();
    }

    public boolean isOperandLeftActive() {
        return operandLeftActive;
    }

    public boolean isOperandLeftDisplay() {
        return operandLeftDisplay;
    }

    public char getOperator() {
        return operator;
    }

    public String getOperandLeft() {
        return operandLeft;
    }

    public String getOperandRight() {
        return operandRight;
    }

    public interface CalcModelChangeObserver {
        public void notifyChange();
        public void notifyArithmeticException();
    }
}
