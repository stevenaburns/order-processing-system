/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orderprocessing;

public class AccountLedger {
    private int totalSalesPrice;
    private int totalSalesCost;
    private int totalSalesUnits;
    private int totalReturnUnits;
    private int totalReturnPrice;
    private int totalReturnCost;
    
    /**
     * @return the totalSalesPrice
     */
    public int getTotalSalesPrice() {
        return totalSalesPrice;
    }

    /**
     * @param totalSalesPrice the totalSalesPrice to set
     */
    public void setTotalSalesPrice(int totalSalesPrice) {
        this.totalSalesPrice = totalSalesPrice;
    }

    /**
     * @return the totalSalesCost
     */
    public int getTotalSalesCost() {
        return totalSalesCost;
    }

    /**
     * @param totalSalesCost the totalSalesCost to set
     */
    public void setTotalSalesCost(int totalSalesCost) {
        this.totalSalesCost = totalSalesCost;
    }

    /**
     * @return the totalSalesUnits
     */
    public int getTotalSalesUnits() {
        return totalSalesUnits;
    }

    /**
     * @param totalSalesUnits the totalSalesUnits to set
     */
    public void setTotalSalesUnits(int totalSalesUnits) {
        this.totalSalesUnits = totalSalesUnits;
    }

    /**
     * @return the totalReturnUnits
     */
    public int getTotalReturnUnits() {
        return totalReturnUnits;
    }

    /**
     * @param totalReturnUnits the totalReturnUnits to set
     */
    public void setTotalReturnUnits(int totalReturnUnits) {
        this.totalReturnUnits = totalReturnUnits;
    }

    /**
     * @return the totalReturnPrice
     */
    public int getTotalReturnPrice() {
        return totalReturnPrice;
    }

    /**
     * @param totalReturnPrice the totalReturnPrice to set
     */
    public void setTotalReturnPrice(int totalReturnPrice) {
        this.totalReturnPrice = totalReturnPrice;
    }

    /**
     * @return the totalReturnCost
     */
    public int getTotalReturnCost() {
        return totalReturnCost;
    }

    /**
     * @param totalReturnCost the totalReturnCost to set
     */
    public void setTotalReturnCost(int totalReturnCost) {
        this.totalReturnCost = totalReturnCost;
    }
}
