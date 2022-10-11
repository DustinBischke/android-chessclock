package com.chess.clock.engine;

import android.content.Context;

import com.chess.clock.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeControlDefaults {

    private static final List<TimePreset> PRESETS = Arrays.asList(
            new TimePreset(1),
            new TimePreset(1, 1),
            new TimePreset(2, 1),
            new TimePreset(3),
            new TimePreset(3, 2),
            new TimePreset(5),
            new TimePreset(5, 5),
            new TimePreset(10),
            new TimePreset(15, 10),
            new TimePreset(20),
            new TimePreset(30)
    );

    /**
     * index of `10 min` preset
     */
    public static int DEFAULT_TIME_INDEX = 7;

    /**
     * Creates default TimeControl list, saves it on shared preferences.
     *
     * @return Default TimeControl list.
     */
    public static ArrayList<TimeControlWrapper> buildDefaultTimeControlsList(Context context) {

        ArrayList<TimeControlWrapper> timeControls = new ArrayList<>();
        for (TimePreset timePreset : PRESETS) {
            timeControls.add(timePreset.toTimeControl(context));
        }

        // Saving default time controls
        TimeControlParser.saveTimeControls(context, timeControls);

        return timeControls;
    }

    private static class TimePreset {
        int minutes;
        int incrementSeconds;

        TimePreset(int minutes) {
            this.minutes = minutes;
            incrementSeconds = 0;
        }

        TimePreset(int minutes, int incrementSeconds) {
            this.minutes = minutes;
            this.incrementSeconds = incrementSeconds;
        }

        String getName(Context context) {
            if (incrementSeconds == 0) {
                return context.getString(R.string.x_min, minutes);
            } else {
                return context.getString(R.string.x_min_y_sec, minutes, incrementSeconds);
            }
        }

        TimeControlWrapper toTimeControl(Context context) {
            Stage stage = new Stage(0, minutes * 60 * 1000L);
            TimeIncrement timeIncrement = new TimeIncrement(TimeIncrement.Type.FISCHER, incrementSeconds * 1000L);
            TimeControl timeControl = new TimeControl(getName(context), new Stage[]{stage}, timeIncrement);
            return new TimeControlWrapper(timeControl, timeControl);
        }
    }

}