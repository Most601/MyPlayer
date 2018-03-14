package metaextract.nkm.com.myplayer;

/**
 *The object SeekBar named songProgressBar using this class.
 */
public class Utilities {
    /**
     * Function to convert milliseconds time to Timer Format Hours:Minutes:Seconds.
     * Using this function to display the duration of the song and the current position in milliseconds.
     * @param milliseconds - time in milliseconds.
     * */
    public String milliSecondsToTimer(long milliseconds){
        String finalTimerString = ""; //the final string that represents the format Hours:Minutes:Seconds.
        String secondsString; //string that represents the seconds.

        //Convert total duration into time.
        int hours = (int)( milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);

        //Add hours if there.
        if(hours > 0){
            finalTimerString = hours + ":";
        }

        //Prepending 0 to seconds if it is one digit.
        if(seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;
        }
        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        //Return timer string.
        return finalTimerString;
    }

    /**
     * Function to Calculate progress percentage (from milliseconds to percentage - integer).
     * The songProgressBar using the percentage value to move himself to the right position in the SeekBar.
     * @param currentDuration - the current time in the song (milliseconds).
     * @param totalDuration - the song length (milliseconds).
     * */
    public int getProgressPercentage(long currentDuration, long totalDuration){
        Double percentage;

        //Convert to seconds.
        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        //Calculating percentage.
        percentage =(((double)currentSeconds)/totalSeconds)*100;

        //Return percentage as integer.
        return percentage.intValue();
    }

    /**
     * Function to convert from progress percentage to milliseconds.
     * The function onStopTrackingTouch using this function to update the media player to the right position in the SeekBar.
     * @param progress - the percentage progress of songProgressBar.
     * @param totalDuration - duration of the song.
     * */
    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration;
        totalDuration = (totalDuration / 1000);
        currentDuration = (int) ((((double)progress) / 100) * totalDuration);

        //Return current duration in milliseconds
        return currentDuration * 1000;
    }
}
