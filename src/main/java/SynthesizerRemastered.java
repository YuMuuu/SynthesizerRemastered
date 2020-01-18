import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SynthesizerRemastered {
    private boolean shouldGenerate;
    private int wavePos;

    private final JFrame frame = new JFrame(" Synthsizer Remastered");
    private final AudioThread audioThread = new AudioThread(() -> {
        if (!shouldGenerate) {
            return null;
        }
        short[] s = new short[AudioThread.BUFFER_SIZE];
        for ( int i = 0 ; i < AudioThread.BUFFER_SIZE; ++i) {
            s[i] = (short)(Short.MAX_VALUE * Math.sin((2 * Math.PI * 440) / AudioInfo.SAMPLE_RATE * wavePos++));
        }
        return s;
    });

    SynthesizerRemastered() {
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(!audioThread.isRunning()) {
                    shouldGenerate = true;
                    audioThread.triggerPlayback();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                shouldGenerate = false;


            }
        });
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

    public static class AudioInfo {
        public static final int SAMPLE_RATE = 44100;
    }
}
