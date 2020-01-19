import utils.Utils;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Random;

public class Oscillator extends SynthControlContainer {

    private static final double FREQUENCY = 440;

    private final Random random  = new Random();

    private Waveform waveform = Waveform.Sine;
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
        setSize(279, 100);
        setBorder(Utils.windowsDesign.LINE_BORDER);
        setLayout(null);
    }

    private enum Waveform {
        Sine, Square, Saw, Triangle, Noise
    }

    public double nextSample() {
        double tDivP = (wavePos++ / (double) SynthesizerRemastered.AudioInfo.SAMPLE_RATE) / (1d / FREQUENCY);
        switch (waveform) {
            case Sine:
                return Math.sin(Utils.Math.frequencyToAngularFrequency(FREQUENCY) * (wavePos - 1) / SynthesizerRemastered.AudioInfo.SAMPLE_RATE);
            case Square:
                return Math.signum(Math.sin(Utils.Math.frequencyToAngularFrequency(FREQUENCY) * (wavePos - 1) / SynthesizerRemastered.AudioInfo.SAMPLE_RATE));
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
}
