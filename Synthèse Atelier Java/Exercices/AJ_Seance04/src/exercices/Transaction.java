package exercices;

public class Transaction {

  static enum TransactionLevel {
    VeryHi, Hi, Lo, Me;
  }

  private Trader trader;
  private int year;
  private int value;
  private TransactionLevel type;

  public Transaction(Trader trader, int year, int value) {
    this.trader = trader;
    this.year = year;
    this.value = value;
  }

  public Trader getTrader() {
    return this.trader;
  }

  public int getYear() {
    return this.year;
  }

  public int getValue() {
    return this.value;
  }

  public String toString() {
    return "{" + this.trader + ", " + "year: " + this.year + ", " + "value:" + this.value + "}";
  }
  
  public TransactionLevel getType() {
    return this.type;
  }
}
