import utils.Utils;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;

public class SynthesizerRemastered {
    private static final HashMap<Character, Double> KEY_FREQUENCYIES = new HashMap<>();

    private boolean shouldGenerate;

    private final Oscillator[] oscillators = new Oscillator[3];
    private final JFrame frame = new JFrame(" Synthsizer Remastered");
    private final AudioThread audioThread = new AudioThread(() -> {
        if (!shouldGenerate) {
            return null;
        }

        short[] s = new short[AudioThread.BUFFER_SIZE];
        for (int i = 0; i < AudioThread.BUFFER_SIZE; ++i) {
            double d = 0;
            for (Oscillator o : oscillators) {
                d += o.nextSample() / oscillators.length;

            }
            s[i] = (short)(Short.MAX_VALUE * d);
        }

        return s;
    });

    private final KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if(!audioThread.isRunning()) {
                for ( Oscillator o: oscillators) {
                    o.setKeyFrequency(KEY_FREQUENCYIES.get(e.getKeyChar()));
                }
                shouldGenerate = true;
                audioThread.triggerPlayback();
            }
        }

        @Override
        public void keyReleased(KeyEvent e ) {
            shouldGenerate = false;
        }
    };

    static {
        final int STARTING_KEY = 16;
        final int KEY_FREQUENCY_INCREMENT =2;
        final char[] KEYS = "zxcvbnm,./asdfghjkl;'#qwertyuiop[]".toCharArray();
        for(int i = STARTING_KEY, key = 0; i < KEYS.length * KEY_FREQUENCY_INCREMENT + STARTING_KEY; i += KEY_FREQUENCY_INCREMENT, ++key) {
            KEY_FREQUENCYIES.put(KEYS[key], Utils.Math.getKeyFrequency(i));
        }

    }


    SynthesizerRemastered() {

        int y = 0;
        for (int i = 0; i < oscillators.length; ++i) {
            oscillators[i] = new Oscillator(this);
            oscillators[i].setLocation(5, y);
            frame.add(oscillators[i]);
            y += 105;
        }

        Oscillator o = new Oscillator(this);
        o.setLocation(5, 0);
        frame.add(o);

        frame.addKeyListener(keyAdapter);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                audioThread.close();
            }
        });
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(613, 357);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public KeyAdapter getKeyAdapter() {
        return keyAdapter;
    }

    public static class AudioInfo {
        public static final int SAMPLE_RATE = 44100;
    }
}
