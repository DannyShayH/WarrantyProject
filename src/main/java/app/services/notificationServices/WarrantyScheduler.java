package app.services.notificationServices;

import app.daos.WarrantyDAO;
import app.entity.Warranty;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WarrantyScheduler {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private final WarrantyDAO warrantyDAO;
    private final EmailService emailService;

    public WarrantyScheduler(WarrantyDAO warrantyDAO, EmailService emailService){
        this.warrantyDAO = warrantyDAO;
        this.emailService = emailService;
    }

    public void start(){
        Runnable task = () -> {
            System.out.println("Checking Warranty");
            checkWarranties();
        };

        long initialDelay = computeInitialDelay();
        long period = TimeUnit.DAYS.toSeconds(1);

        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    public void checkWarranties(){
        Set<Warranty> warranties = warrantyDAO.get();
        System.out.println("Total warranties found: " + warranties.size());
        LocalDate today = LocalDate.now();

        for(Warranty warranty : warranties){
            long daysLeft = ChronoUnit.DAYS.between(today, warranty.calculateEndDate());
            System.out.println("Warranty ID: " + warranty.getId() + "days left: " + daysLeft);

            if(daysLeft == 90 && !warranty.isNotified90Days()) {
            sendAndMark(warranty, 90);
            } else if(daysLeft == 60 && !warranty.isNotified60Days()){
                sendAndMark(warranty, 60);
            } else if(daysLeft == 30 && !warranty.isNotified30Days()){
                sendAndMark(warranty, 30);
            } else if(daysLeft == 0 && !warranty.isNotifiedExpired()){
                sendAndMark(warranty, 0);
            }
        }
    }
    private void sendAndMark(Warranty warranty, long days) {
        if(warranty.getProduct() == null || warranty.getProduct().getOwner() == null){
            System.out.println("Product or owner is null on warranty with Id - " + warranty.getId());
            return;
        }
        emailService.sendWarrantyReminder(
                warranty.getProduct().getOwner().getEmail(),
                warranty.getProduct().getProductName(),
                days);

        switch ((int) days) {
        case 90 -> warranty.setNotified90Days(true);
        case 60 -> warranty.setNotified60Days(true);
        case 30 -> warranty.setNotified30Days(true);
        case 0 -> warranty.setNotifiedExpired(true);
        }
        warrantyDAO.update(warranty);
    }

    private long computeInitialDelay(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.withHour(9).withMinute(0).withSecond(0);

        if(now.isAfter(nextRun)){
        nextRun = nextRun.plusDays(1);
        }
        return Duration.between(now, nextRun).getSeconds();
    }
}