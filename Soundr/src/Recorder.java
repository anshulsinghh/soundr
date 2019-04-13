import java.io.IOException;
import java.util.LinkedList;
import javax.sound.sampled.*;

public class Recorder extends Thread {

  public static final int BUFFER_BYTE_SIZE = 2048;

  public static float threshold;
  public static boolean aboveThreshold = false;
  public static boolean canPrint = true;
  public static LinkedList<Float> floatList = new LinkedList<Float>();
  public static int currentIdx = -1;
  public static int duplicateControl = 0;
  public boolean stop = false;

  public Recorder() {
    threshold = 0.5f;
  }

  public void setThreshold(float f) {
    threshold = f;
    System.out.println("setting threshold to: " + f);
  }

  public void run() {

    AudioFormat format =
        new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100f, 16, 2, 4, 44100f, false);


    TargetDataLine targetLine;
    try {
      targetLine = AudioSystem.getTargetDataLine(format);
      targetLine.open(format, BUFFER_BYTE_SIZE);
    } catch (LineUnavailableException e) {
      System.err.println(e);
      return;
    }

    byte[] buf = new byte[BUFFER_BYTE_SIZE];
    float[] samples = new float[BUFFER_BYTE_SIZE / 2]; // half the size due to the samples being
                                                       // stereo

    targetLine.start();
    // Loops as long as there are items in the buffer array
    for (int b; (b = targetLine.read(buf, 0, buf.length)) > -1 && !stop;) {

      // loop allows us to turn raw audio bytes into floats that we can read
      for (int i = 0, s = 0; i < b;) {
        int sample = 0;

        sample |= buf[i++] & 0xFF; // 1. & 0xFF makes it so that no matter what the values are
                                   // between 0-255 (avoiding sign extension)
                                   // 2. |= will set sample here to equal whatever buf[i++] & 0xFF
                                   // is. (bitwise | is unnecessary)
        sample |= buf[i++] << 8; // 1. << 8 will bit shift our current byte/sample to the left by 8
                                 // because our bits per sample is 16

        float f = (float) Math.pow(2, 15);
        samples[s++] = sample / f; // this normalizes the data in samples so that each sample will
                                   // be ratio'd into a number
                                   // between -1f and 1f. This is done by dividing by 2^(16-1).
      }


      float peak = 0f;
      for (float sample : samples) {

        float abs = Math.abs(sample);
        // the first value will become the new peak
        if (abs > peak) {
          peak = abs;
        }
      }

      // System.out.println(peak);

      try {
        sendNotification(peak);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      // dynamicThreshold(peak);
    }
  }

  public static void sendNotification(float peak) throws IOException {
    if (!aboveThreshold && peak > threshold && canPrint) {
      // Runtime.getRuntime().exec(new String[] { "osascript", "-e", "display notification
      // \"Background Noise Alert\" with title \"Soundr\" sound name \"Funk\"" });


      System.out.println("Notifying user");
      System.out.println("");
      aboveThreshold = true;

      canPrint = false;

      new java.util.Timer().schedule(new java.util.TimerTask() {
        @Override
        public void run() {
          System.out.println("Resetting timer");
          canPrint = true;
        }
      }, 5000);
    } else if (aboveThreshold && peak <= threshold) {
      aboveThreshold = false;
    }
  }

  public static void dynamicThreshold(float peak) throws IOException {
    floatList.add(peak); // add new audio volume
    currentIdx++; // keeps track of which is the current audio value to test
    duplicateControl--; // makes it so that there must be a cooldown of 5 newly added audio
                        // values before the program can run again

    // duplicateControl; and must be at least 12 audio values
    if (duplicateControl <= 0 && floatList.size() > 11) {
      float avg = 0f;
      // gets the average of the previous 10 values before the current audio value
      for (int i = currentIdx - 1; i >= currentIdx - 10; i--) {
        avg += floatList.get(i);
      }
      avg = avg / 10;

      float diff = (floatList.get(currentIdx) - avg); // finds the difference between the current
                                                      // audio value and the average of the previous
                                                      // 10
      // System.out.println(floatList.get(currentIdx));
      // if the difference is greater than .4 then the app will notify you
      if (diff >= .4f) {
        System.out.println("NOTIFYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
        duplicateControl = 10; // duplicate control set to 5 so that there must be
                               // 5 new values before the app can run again
      }
      floatList.remove(); // removes the front node so that the linked list maintains a size of 11
                          // so that
                          // there isn't an insane memory usage
      currentIdx--; // must decrement index because you are removing a value, index at this point
                    // will remain static
    }
  }
}