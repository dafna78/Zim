package utilities;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

/**
 * A screen recorder class.
 * Captures the screen, the mouse cursor and audio.
 */
public class MonteScreenRecorder extends ScreenRecorder
{
    public static ScreenRecorder screenRecorder;
    public static String name;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public MonteScreenRecorder(GraphicsConfiguration cfg, Rectangle captureArea,
                               Format fileFormat, Format screenFormat,
                               Format mouseFormat, Format audioFormat,
                               File movieFolder, String name) throws IOException, AWTException
    {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        MonteScreenRecorder.name = name + "_" + sdf.format(timestamp);
    }

    /**
     * Creates a file for recording the movie in the users "test-recordings" folder.
     * @param fileFormat file format
     * @return the movie file
     * @throws IOException throws an exception when the movie folder is not a directory
     */
    @Override
    protected File createMovieFile(Format fileFormat) throws IOException
    {
        if (!movieFolder.exists())
        {
            movieFolder.mkdirs();
        }
        else if (!movieFolder.isDirectory())
        {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }

        System.out.println("file is now created");
        return new File(movieFolder, name + "." + Registry.getInstance().getExtension(fileFormat));

    }

    /**
     * Starts recording the screen.
     * @param methodName method (test) name
     * @throws Exception Can throw an IO exception when creating a screen recorder or start recording
     */
    public static void startRecord(String methodName) throws Exception
    {
        File file = new File("./test-recordings/");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        Rectangle captureSize = new Rectangle(0, 0, width, height);

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().
                getDefaultScreenDevice()
                .getDefaultConfiguration();

        screenRecorder = new MonteScreenRecorder(gc, captureSize,
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
                        Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                null, file, methodName);

        screenRecorder.start();
    }

    /**
     * Stops the recording
     * @throws Exception May throw an IO exception - Stopping the screen recorder may take several seconds
     */
    public static void stopRecord() throws Exception
    {
        screenRecorder.stop();
        System.out.println("file stop recording");
    }
}
