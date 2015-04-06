package com.example.android.cs3218thearith.cs3218thearith.SurfaceView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

/**
 * Created by thearith on 1/4/15.
 */
public class CSurfaceViewCorrelationFFT extends SurfaceView implements SurfaceHolder.Callback{
    private Context drawContext;
    public  DrawThread       drawThread;
    private SurfaceHolder    drawSurfaceHolder;
    private Boolean          threadExists = false;
    public static volatile   Boolean drawFlag = false;

    private static final Handler handler = new Handler(){

        public void handleMessage(Message paramMessage)
        {
        }
    };



    public CSurfaceViewCorrelationFFT(Context ctx, AttributeSet attributeSet)
    {
        super(ctx, attributeSet);

        drawContext = ctx;

        init();

    }


    public void init()
    {

        if (!threadExists) {

            drawSurfaceHolder = getHolder();
            drawSurfaceHolder.addCallback(this);

            drawThread = new DrawThread(drawSurfaceHolder, drawContext, handler);

            drawThread.setName("" +System.currentTimeMillis());
            drawThread.start();
        }

        threadExists = Boolean.valueOf(true);
        drawFlag = Boolean.valueOf(true);

        return;

    }


    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
    {
        drawThread.setSurfaceSize(paramInt2, paramInt3);
    }


    public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
    {

        init();

    }


    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
    {

        while (true)
        {
            if (!drawFlag)
                return;
            try
            {
                drawFlag = Boolean.valueOf(false);
                drawThread.join();
            }
            catch (InterruptedException localInterruptedException)
            {

            }
        }

    }


    public class DrawThread extends Thread
    {
        private Bitmap backgroundImage;
        private SurfaceHolder  surfaceHolder;

        private Paint redPaint;
        private Paint bluePaint;
        private Paint greenPaint;


        private double[] convol;
        private double[] xr;
        private double[] xs;

        public DrawThread(SurfaceHolder paramContext, Context paramHandler, Handler arg4)
        {
            surfaceHolder = paramContext;

            redPaint = new Paint();
            redPaint.setARGB(255, 255, 0, 0);
            redPaint.setStrokeWidth(5);

            bluePaint = new Paint();
            bluePaint.setARGB(255, 0, 0, 255);
            bluePaint.setStrokeWidth(5);

            greenPaint = new Paint();
            greenPaint.setARGB(255, 0, 255, 0);
            greenPaint.setStrokeWidth(5);


            backgroundImage = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);

            convol = new double[512];
            xr = new double[512];
            double[] xrFFT = new double[2*512];
            xs = new double[512];
            double[] xsFFT = new double[2*512];
            double[] convolFFT = new double[2*512];

            for(int t=50; t<=250; t++) {
                xr[t] = 2;
            }

            for(int t=50; t<=150; t++) {
                xs[t] = 1-((t-100)+50)/100.0;
            }

            //transform
            int segmentIndex = 0;
            while (segmentIndex < 512) {

                xrFFT[2 * segmentIndex] = xr[segmentIndex];
                xrFFT[2 * segmentIndex + 1] = 0.0;

                xsFFT[2 * segmentIndex] = xs[segmentIndex];
                xsFFT[2 * segmentIndex + 1] = 0.0;
                segmentIndex++;
            }

            DoubleFFT_1D fft = new DoubleFFT_1D(512);
            fft.complexForward(xsFFT);
            fft.complexForward(xrFFT);

            for(int t=0; t<512; t++) {
                // (a+bi)(c-di) = (ac+bd) + (-ad+bc)i
                convolFFT[2*t] = xrFFT[2*t]*xsFFT[2*t] + xrFFT[2*t+1]*xsFFT[2*t+1]; // ac + bd
                convolFFT[2*t+1] = -xrFFT[2*t]*xsFFT[2*t+1] + xrFFT[2*t+1]*xsFFT[2*t];	// -ad + bc
            }

            fft.complexInverse(convolFFT, true);
            for(int t=0; t<2*512; t+=2) {
                convol[t/2] = convolFFT[t];
            }

        }

        public void doDraw(Canvas canvas) {

            int canvasHeight = canvas.getHeight();
            int canvasWidth = canvas.getWidth();
            int h = canvasHeight / 11;
            int axisRate = h / 2;

            Paint paint = new Paint();
            paint.setColor(Color.YELLOW);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPaint(paint);

            //draw red line
            int height = h*2;
            int xStart, xStop, yStart, yStop;

            for(int t=-200; t<=200; t++) {
                xStart = canvasWidth / 2 + t;

                if(t>-100 && t < 100) {
                    xStop = xStart + 1;
                    yStart = yStop = -2*axisRate + height;

                } else if(t==-100 || t==100) {
                    xStop = xStart;
                    yStart = height;
                    yStop = -2*axisRate + height;

                } else {
                    xStop = xStart + 1;
                    yStart = yStop = height;
                }

                canvas.drawLine(xStart, yStart, xStop, yStop, redPaint);
            }

            //draw blue line
            height = h*4;
            for(int t=-200; t<=200; t++) {
                xStart = canvasWidth / 2 + t;

                if(t>-50 && t<50) {
                    xStop = xStart + 1;
                    yStart = (int)(-(1-(t+50)/100.0)*axisRate + height);
                    yStop =  (int)(-(1-(t+1+50)/100.0)*axisRate + height);


                } else if(t==-50) {
                    xStop = xStart;
                    yStart = height;
                    yStop = -axisRate + height;
                } else {
                    xStop = xStart + 1;
                    yStart = yStop = height;
                }

                canvas.drawLine(xStart, yStart, xStop, yStop, bluePaint);
            }



            //draw convolution result
            height = h*6;
            for(int t=0; t<511; t++) {
                xStart = canvasWidth / 2 + (t-512/2);
                xStop = xStart + 1;
                yStart = (int)(convol[t]) + height;
                yStop = (int)(convol[t+1]) + height;

                canvas.drawLine(xStart, yStart, xStop, yStop, greenPaint);
            }
        }

        public void setSurfaceSize(int canvasWidth, int canvasHeight)
        {
            synchronized (surfaceHolder)
            {
                backgroundImage = Bitmap.createScaledBitmap(backgroundImage, canvasWidth, canvasHeight, true);
                return;
            }
        }

        public void run()
        {
            while (drawFlag)
            {

                Canvas localCanvas = null;
                try
                {
                    localCanvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder)
                    {
                        if (localCanvas != null) {
                            doDraw(localCanvas);
                        }
                    }
                }
                finally
                {
                    if (localCanvas != null)
                        surfaceHolder.unlockCanvasAndPost(localCanvas);
                }
            }
        }

    }
}
