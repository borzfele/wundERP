package wundERP.models.comment;

import wundERP.models.DailyAccount;

public class DailyAccountComment extends Comment {

    private DailyAccount dailyAccount;

    public DailyAccount getDailyAccount() {
        return dailyAccount;
    }

    public void setDailyAccount(DailyAccount dailyAccount) {
        this.dailyAccount = dailyAccount;
    }
}
