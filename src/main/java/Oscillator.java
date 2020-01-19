import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Oscillator extends SynthControlContainer {

    private static final int TONE_OFFSET_LIMIT = 2000;

    private final Random random = new Random();

    private Waveform waveform = Waveform.Sine;
    private double keyFrequency;
    private double frequency;
    private int toneOffset;
    private int wavePos;

    public Oscillator(SynthesizerRemastered synth) {
        super(synth);
        JComboBox<Waveform> comboBox = new JComboBox(new Waveform[]{
                Waveform.Sine, Waveform.Square, Waveform.Saw, Waveform.Triangle, Waveform.Noise
        });
        comboBox.setSelectedItem(Waveform.Sine);
        comboBox.setBounds(10, 10, 80, 25);
        comboBox.addItemListener(l -> {
                    if (l.getStateChange() == ItemEvent.SELECTED) {
                        waveform = (Waveform) l.getItem();
                    }
                }
        );
        add(comboBox);
        JLabel toneParameter = new JLabel("x0.00");
        toneParameter.setBounds(165, 65, 50, 25);
        toneParameter.setBorder(Utils.windowsDesign.LINE_BORDER);
        toneParameter.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                final Cursor BLANK_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB)
                        , new Point(0, 0), "blank_cursor");
                setCursor(BLANK_CURSOR);
                mouseClickLocation = e.getLocationOnScreen();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
        toneParameter.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mouseClickLocation.y != e.getYOnScreen()) {
                    boolean mouseMovingUp = mouseClickLocation.y - e.getYOnScreen() > 0;
                    if (mouseMovingUp && toneOffset <TONE_OFFSET_LIMIT) {
                        ++toneOffset;
                    } else if (!mouseMovingUp && toneOffset > -TONE_OFFSET_LIMIT) {
                        --toneOffset;
                    }
                    applyToneOffset();
                    toneParameter.setText("x" + String.format("%3f", getToneOffset()));
                }
                Utils.ParameterHandring.PARAMETER_ROBOT.mouseMove(mouseClickLocation.x, mouseClickLocation.y);
            }
        });
        add(toneParameter);
        JLabel toneText = new JLabel("tone");
        toneText.setBounds(172, 40, 75, 25);
        add(toneText);
        setSize(279, 100);
        setBorder(Utils.windowsDesign.LINE_BORDER);
        setLayout(null);
    }

    private enum Waveform {
        Sine, Square, Saw, Triangle, Noise
    }

    public double getKeyFrequency() {
        return frequency;
    }

    public void setKeyFrequency(double frequency) {
        keyFrequency = this.frequency = frequency;
        applyToneOffset();
    }


    private double getToneOffset(){
        return toneOffset / 100d;
    }


    public double nextSample() {
        double tDivP = (wavePos++ / (double) SynthesizerRemastered.AudioInfo.SAMPLE_RATE) / (1d / frequency);
        switch (waveform) {
            case Sine:
                return Math.sin(Utils.Math.frequencyToAngularFrequency(frequency) * (wavePos - 1) / SynthesizerRemastered.AudioInfo.SAMPLE_RATE);
            case Square:
                return Math.signum(Math.sin(Utils.Math.frequencyToAngularFrequency(frequency) * (wavePos - 1) / SynthesizerRemastered.AudioInfo.SAMPLE_RATE));
            case Saw:
                return 2d * (tDivP - Math.floor(0.5 + tDivP));
            case Triangle:
                return 2d * Math.abs(2d * (tDivP - Math.floor(0.5 + tDivP))) - 1;
            case Noise:
                return random.nextDouble();
            default:
                throw new RuntimeException("Oscillator set to unknown waveform");
        }
    }

    private void applyToneOffset() {
        frequency = keyFrequency * Math.pow(2, getToneOffset());
        System.out.println(frequency);
    }
}
