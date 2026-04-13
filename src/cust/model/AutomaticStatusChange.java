package cust.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class AutomaticStatusChange {
    public static void computeStatus(AccountHolder accountHolder, CUSTModel model, boolean testForDay) {
        if (accountHolder.getStatus().equals(AccountHolder.AccountStatus.IN_DEFAULT))
            return;

        int currentDay = LocalDate.now().getDayOfMonth();

        //currentDay = 16;
        if (testForDay && currentDay < 15)
            return;

        ArrayList<Order> orders = model.getOrdersByAccount(accountHolder.getAccountId());

        Month lastMonth = LocalDate.now().getMonth().minus(1);
        Month twoMonths = LocalDate.now().getMonth().minus(2);

        boolean lastMonthPaymentDue = false;
        boolean twoMonthPaymentDue = false;

        for (Order o : orders) {
            if (!o.isPaid()) {
                if (LocalDate.parse(o.getOrderDate()).getMonth() == lastMonth) {
                    lastMonthPaymentDue = true;
                }else if (LocalDate.parse(o.getOrderDate()).getMonth() == twoMonths) {
                    twoMonthPaymentDue = true;
                    break;
                }
            }
        }

        if (twoMonthPaymentDue){
            model.setStatus(accountHolder.getAccountId(), AccountHolder.AccountStatus.IN_DEFAULT.toString());
            model.set2ndReminderStatus(accountHolder.getAccountId(), AccountHolder.ReminderStatus.DUE.toString());
            return;
        }

        if (lastMonthPaymentDue){
            model.setStatus(accountHolder.getAccountId(), AccountHolder.AccountStatus.SUSPENDED.toString());
            model.set1stReminderStatus(accountHolder.getAccountId(), AccountHolder.ReminderStatus.DUE.toString());
            return;
        }

        model.setStatus(accountHolder.getAccountId(), AccountHolder.AccountStatus.NORMAL.toString());
        model.set1stReminderStatus(accountHolder.getAccountId(), AccountHolder.ReminderStatus.NO_NEED.toString());
        model.set2ndReminderStatus(accountHolder.getAccountId(), AccountHolder.ReminderStatus.NO_NEED.toString());

        /*
        Month currentMonth = LocalDate.now().getMonth();
        Month lastMonth = currentMonth.minus(1);
        Month nextMonth = currentMonth.plus(1);

        int currentDay = LocalDate.now().getDayOfMonth();

        LocalDate t1 = LocalDate.parse("2026-01-01");
        LocalDate t2 = LocalDate.parse("2026-02-01");
        LocalDate t3 = LocalDate.parse("2026-03-01");
        LocalDate t4 = LocalDate.parse("2026-04-01");

        ArrayList<LocalDate> dates = new ArrayList<>();
        dates.add(t1);
        dates.add(t2);
        dates.add(t3);
        dates.add(t4);

        for (LocalDate d : dates){
            System.out.println(d.toString() + " is Current: " + (d.getMonth() == currentMonth) + " - is Last: " + (d.getMonth() == lastMonth));
        }
        */
    }
}
