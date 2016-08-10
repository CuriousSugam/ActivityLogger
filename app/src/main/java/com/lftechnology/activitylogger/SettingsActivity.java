package com.lftechnology.activitylogger;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.SwitchCompat;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lftechnology.activitylogger.broadcastReceiver.NotificationBroadcastReceiver;
import com.lftechnology.activitylogger.services.AppUsageAlertService;
import com.lftechnology.activitylogger.utilities.SettingsData;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * It contains the option whether to set the notification for regular monitoring of app usage statistics
 * It provides the option to set the alert the user gets when the user continuously uses
 * and application for a specified period of time.
 * <p/>
 * It contains the user interface to select the notification time.
 * It provides the option to select the application usage duration to specify after how long the user
 * will be notified.
 *
 * @author Sugam Shakya
 */
public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.notification_icon)
    ImageView notificationIcon;

    @BindView(R.id.txt_notification_status)
    TextView notificationStatusTextView;

    @BindView(R.id.switch_notification_status)
    SwitchCompat notificationStatusSwitch;

    @BindView(R.id.txt_regular_notification)
    TextView regularNotificationTextView;

    @BindView(R.id.txt_regular_notification_time)
    TextView regularNotificationTime;

    @BindView(R.id.txt_app_usage_alert_status)
    TextView appUsageAlertStatus;

    @BindView(R.id.switch_app_usage_alert)
    SwitchCompat appUsageAlertSwitch;

    @BindView(R.id.txt_app_usage_alert)
    TextView appUsageAlertTextView;

    @BindView(R.id.txt_app_usage_alert_time)
    TextView appUsageAlertTimeTextView;

    @BindView(R.id.notification_time_selection_container)
    RelativeLayout linearLayout;

    @BindView(R.id.alert_usage_duration_container)
    RelativeLayout alertLinearLayout;

    @BindView(R.id.settings_container)
    LinearLayout settingsContainerLinearLayout;


    public final static String ALERT_TIME_MILLIS = "alert_time_in_millis";
    public final static String ALERT_STATUS = "alert_status";

    private SettingsData settingsData;
    private Calendar notificationCalendarObj;
    private long alertTimeInMillis;
    private boolean alertStatusChanged = false;
    private boolean alertStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        settingsData = new SettingsData(this);
        // check if the  notification is set or not
        //  if yes check mark the switch and display the time selection layout
        if (settingsData.getNotificationStatus()) {
            notificationIcon.setImageResource(R.drawable.ic_notifications_black_24dp);
            notificationStatusSwitch.setChecked(true);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            notificationIcon.setImageResource(R.drawable.ic_notifications_off_black_24dp);
            notificationStatusSwitch.setChecked(false);
            linearLayout.setVisibility(View.GONE);
        }

        // display the notification time that is/was set
        long notificationTime = settingsData.getNotificationTime();
        notificationCalendarObj = Calendar.getInstance();
        notificationCalendarObj.setTimeInMillis(notificationTime);
        int hour, minute;
        hour = notificationCalendarObj.get(Calendar.HOUR_OF_DAY);
        minute = notificationCalendarObj.get(Calendar.MINUTE);
        if (hour == 0) {
            regularNotificationTime.setText("Not Set");
        } else {
            regularNotificationTime.setText(timeToString(hour, minute));
        }

        // check if the app usage alert was set
        // if yes, check mark the switch and display the time selection layout
        if (settingsData.getUsageAlertStatus()) {
            alertLinearLayout.setVisibility(View.VISIBLE);
            appUsageAlertSwitch.setChecked(true);
            alertStatus = true;
        } else {
            appUsageAlertSwitch.setChecked(false);
            alertLinearLayout.setVisibility(View.GONE);
            alertStatus = false;
        }

        // display the alert duration time that is/was set
        alertTimeInMillis = settingsData.getAlertTimeDuration();
        if (alertTimeInMillis == 0) {
            appUsageAlertTimeTextView.setText("Not Set");
        } else {
            int alertHour = (int) (alertTimeInMillis / (1000 * 60 * 60)) % 24;
            int alertMinute = (int) (alertTimeInMillis / (1000 * 60)) % 60;
            appUsageAlertTimeTextView.setText(timeToString(alertHour, alertMinute));
        }

        // notification status switch on check state change listener
        notificationStatusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    notificationIcon.setImageResource(R.drawable.ic_notifications_black_24dp);
                    linearLayout.setVisibility(View.VISIBLE);
                    settingsData.setNotificationStatus(true);
                } else {
                    notificationIcon.setImageResource(R.drawable.ic_notifications_off_black_24dp);
                    linearLayout.setVisibility(View.GONE);
                    settingsData.setNotificationStatus(false);
                }
            }
        });

        // alert status switch on check state change listener
        appUsageAlertSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    alertLinearLayout.setVisibility(View.VISIBLE);
                    settingsData.setUsageAlertStatus(true);
                    alertStatus = true;
                } else {
                    alertLinearLayout.setVisibility(View.GONE);
                    settingsData.setUsageAlertStatus(false);
                    alertStatus = false;
                }
                alertStatusChanged = true;
            }
        });
    }

    /**
     * convert the given hour and minute to string in the form HH:mm
     *
     * @param hour   hour
     * @param minute minute
     * @return HH:mm formatted string of the given hour and minute time
     */
    private String timeToString(int hour, int minute) {
        String hourValue = String.valueOf(hour);
        String minuteValue = String.valueOf(minute);
        if (hour < 10) {
            hourValue = "0" + String.valueOf(hour);
        }
        if (minute < 10) {
            minuteValue = "0" + String.valueOf(minute);
        }
        return hourValue + ":" + minuteValue;
    }


    @Override
    protected void onPause() {
        super.onPause();
        // intiate the service here to monitor the foreground running apps duration
        if(alertStatusChanged){
            Intent serviceIntent = new Intent(SettingsActivity.this, AppUsageAlertService.class);
            serviceIntent.putExtra(ALERT_STATUS, alertStatus);
            if(alertStatus){
                serviceIntent.putExtra(ALERT_TIME_MILLIS, alertTimeInMillis);
            }
            startService(serviceIntent);
        }
    }

    /**
     * method that show the time picker dialog when the notification time is to be changed
     */
    @OnClick({R.id.txt_regular_notification_time, R.id.txt_regular_notification})
    public void showNotificationTimePicker() {
        TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String amPm = (selectedHour < 12) ? "AM" : "PM";
                regularNotificationTime.setText(timeToString(selectedHour, selectedMinute) + amPm);
                // create a pending intent
                Intent intent = new Intent(SettingsActivity.this, NotificationBroadcastReceiver.class);
                Notification notification = prepareNotification();
                intent.putExtra(NotificationBroadcastReceiver.NOTIFICATION_ID, 1);
                intent.putExtra(NotificationBroadcastReceiver.NOTIFICATION, notification);
                // Prepare a pending intent to be initiated when the alarm goes off
                PendingIntent pendingIntent = PendingIntent.getBroadcast(SettingsActivity.this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                setAlarm(selectedHour, selectedMinute, pendingIntent);

            }
        }, notificationCalendarObj.get(Calendar.HOUR_OF_DAY), notificationCalendarObj.get(Calendar.MINUTE), true);
        timePicker.setTitle("Select time");
        timePicker.show();
    }

    /**
     * prepare the heads up notification on the notification panel
     *
     * @return a Notification
     */
    private Notification prepareNotification(){
        // Build a notification to be display with appropriate message
        Notification.Builder notificationBuilder = new Notification.Builder(SettingsActivity.this);
        notificationBuilder.setContentTitle("Time to check activity log");
//                notificationBuilder.setContentText("");
        notificationBuilder.setSmallIcon(R.drawable.applogo).setDefaults(Notification.DEFAULT_ALL);
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);

        // Prepare a pending intent to be initiated when the notification is clicked
        Intent intentForNotification = new Intent(SettingsActivity.this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(SettingsActivity.this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intentForNotification);
        PendingIntent pendingIntentForNotification = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntentForNotification);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        return notification;
    }

    /**
     * set the alarm to trigger on the given time
     *
     * @param selectedHour hour of time when to trigger the alarm
     * @param selectedMinute minute of time when to trigger the alarm
     * @param pendingIntent action to perform when the alarm goes off
     */
    private void setAlarm(int selectedHour, int selectedMinute, PendingIntent pendingIntent){
        Calendar currentTime = Calendar.getInstance();
        notificationCalendarObj = Calendar.getInstance();
        notificationCalendarObj.set(currentTime.get(Calendar.YEAR),currentTime.get(Calendar.MONTH),
                currentTime.get(Calendar.DATE),
                selectedHour, selectedMinute);
        settingsData.setNotificationTime(notificationCalendarObj.getTimeInMillis());

        // Set the alarm to go off when at the selected time
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long interval = 24 * 60 * 60 * 1000;      // convert 24hours to milliseconds
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, notificationCalendarObj.getTimeInMillis(), interval, pendingIntent);
    }

    /**
     * method that show the time picker dialog when the App usage duration alert time is to be changed
     */
    @OnClick({R.id.txt_app_usage_alert, R.id.txt_app_usage_alert_time})
    public void showAlertTimeSelector() {
        int alertHour = (int) (alertTimeInMillis / (1000 * 60 * 60)) % 24;
        int alertMinute = (int) (alertTimeInMillis / (1000 * 60)) % 60;

        TimePickerDialog timePickerDialog = new TimePickerDialog(SettingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                appUsageAlertTimeTextView.setText(timeToString(selectedHour, selectedMinute));
                alertTimeInMillis = (selectedHour * 60 * 60 * 1000) + (selectedMinute * 60 * 1000);
                settingsData.setAlertTimeDuration(alertTimeInMillis);
            }
        }, alertHour, alertMinute, true);
        alertStatusChanged = true;
        timePickerDialog.setTitle("Select Duration");
        timePickerDialog.show();
    }
}
