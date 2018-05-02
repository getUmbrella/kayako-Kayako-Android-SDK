package com.kayako.sdk.android.k5.common.view;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.kayako.sdk.android.k5.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CircleImageView.class,ImageView.class,Paint.class,View.class})
public class CircleImageViewTest {

  private final static String INVALID_BOUNDS_MESSAGE = "adjustViewBounds not supported.";
  private final int padLeft = 5;
  private final int padTop = 5;
  private final int padRight = 5;
  private final int padBottom = 5;
  private final int padStart = 5;
  private final int padEnd = 5;
  private final int width = 120;
  private final int height = 240;

  private CircleImageView circleImageView;

  @Mock
  private Context context;

  @Mock
  private Bitmap bitmap;

  @Mock
  private Drawable drawable;

  @Mock
  private Uri uri;

  @Mock
  private ColorFilter colorFilter;

  @Rule
  private ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    suppress(method(ImageView.class, "setScaleType"));
    suppress(method(ImageView.class, "setImageBitmap"));
    suppress(method(ImageView.class, "getDrawable"));
    suppress(method(ImageView.class, "setImageDrawable"));
    suppress(method(ImageView.class, "setImageResource"));
    suppress(method(ImageView.class, "setImageURI"));
    suppress(method(Paint.class, "setColor"));
    suppress(method(Paint.class, "setColorFilter"));
    suppress(method(View.class, "invalidate"));
    suppress(method(View.class, "getWidth"));
    suppress(method(View.class, "getHeight"));
    suppress(method(View.class, "setPadding"));
    suppress(method(View.class, "onSizeChanged"));
    suppress(method(View.class, "setPaddingRelative"));

    //circleImageView = new CircleImageView(context);
    circleImageView = PowerMockito.spy(new CircleImageView(context));
  }

  @Test
  public void borderColorTest() throws Exception {
    // Arrange
    int borderColor = circleImageView.getBorderColor();
    borderColor++;

    // Act
    circleImageView.setBorderColor(borderColor);

    // Assert
    assertThat(circleImageView.getBorderColor(), is(equalTo(borderColor)));
    PowerMockito.verifyPrivate(circleImageView)
        .invoke("invalidate");
  }

  @Test
  public void borderWidthTest() throws Exception {
    // Arrange
    int borderWidth = circleImageView.getBorderWidth();
    borderWidth++;

    // Act
    circleImageView.setBorderWidth(borderWidth);

    // Assert
    assertThat(circleImageView.getBorderWidth(), is(equalTo(borderWidth)));
    PowerMockito.verifyPrivate(circleImageView)
        .invoke("setup");
  }

  @Test
  public void borderOverlayTest() throws Exception {
    // Arrange
    boolean borderOverlay = circleImageView.isBorderOverlay();
    borderOverlay = !borderOverlay;

    // Act
    circleImageView.setBorderOverlay(borderOverlay);

    // Assert
    assertThat(circleImageView.isBorderOverlay(), is(equalTo(borderOverlay)));
    PowerMockito.verifyPrivate(circleImageView)
        .invoke("setup");
  }

  @Test
  public void disableCircularTransformTest() throws Exception {
    // Arrange
    boolean disableCircularTransform = circleImageView.isDisableCircularTransformation();
    disableCircularTransform = !disableCircularTransform;

    // Act
    circleImageView.setDisableCircularTransformation(disableCircularTransform);

    // Assert
    assertThat(circleImageView.isDisableCircularTransformation(), is(equalTo(disableCircularTransform)));
    PowerMockito.verifyPrivate(circleImageView)
        .invoke("initializeBitmap");
  }

  @Test
  public void checkConstructor() throws Exception {
    // Arrange
    CircleImageView mock = PowerMockito.mock(CircleImageView.class);
    PowerMockito.whenNew(CircleImageView.class)
        .withArguments(context)
        .thenReturn(mock);

    // Act
    CircleImageView civ = new CircleImageView(context);

    // Assert
    PowerMockito.verifyNew(CircleImageView.class)
        .withArguments(context);
  }

  @Test
  public void setScaleTypeDifferent() {
    // Arrange
    ScaleType expected = ScaleType.FIT_END;

    // Assert
    thrown.expect(IllegalArgumentException.class);

    // Act
    circleImageView.setScaleType(expected);
  }

  @Test
  public void setCorrectScaleType() {
    // Arrange
    ScaleType expected = ScaleType.CENTER_CROP;

    // Act
    circleImageView.setScaleType(expected);

    // Assert
    assertThat(circleImageView.getScaleType(), is(equalTo(expected)));
  }

  @Test
  public void throwExceptionIfAdjustViewBoundsIsTrue() {
    // Arrange
    boolean adjustViewBounds = true;

    // Assert
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage(INVALID_BOUNDS_MESSAGE);

    // Act
    circleImageView.setAdjustViewBounds(adjustViewBounds);
  }

  @Test
  public void callSetupOnPaddingChanged() throws Exception {
    // Arrange

    // Act
    circleImageView.setPadding(padLeft, padTop, padRight, padBottom);

    // Assert
    PowerMockito.verifyPrivate(circleImageView)
        .invoke("setup");
  }

  @Test
  public void callSetupOnSizeChanged() throws Exception {
    // Arrange

    // Act
    circleImageView.onSizeChanged(width, height, width+100, height+100);

    // Assert
    PowerMockito.verifyPrivate(circleImageView)
        .invoke("setup");
  }

  @Test
  public void callSetupOnSetPaddingRelative() throws Exception {
    // Arrange

    // Act
    circleImageView.setPaddingRelative(padStart, padTop, padEnd, padBottom);

    // Assert
    PowerMockito.verifyPrivate(circleImageView)
        .invoke("setup");
  }

  @Test
  public void callInitializeBitmapOnSetImageBitmap() throws Exception {
    // Arrange

    // Act
    circleImageView.setImageBitmap(bitmap);

    // Assert
    PowerMockito.verifyPrivate(circleImageView)
        .invoke("initializeBitmap");
  }

  @Test
  public void callInitializeBitmapOnSetImageDrawable() throws Exception {
    // Arrange

    // Act
    circleImageView.setImageDrawable(drawable);

    // Assert
    PowerMockito.verifyPrivate(circleImageView)
        .invoke("initializeBitmap");
  }

  @Test
  public void callInitializeBitmapOnSetImageResource() throws Exception {
    // Arrange

    // Act
    circleImageView.setImageResource(R.drawable.ko__attachment_background);

    // Assert
    PowerMockito.verifyPrivate(circleImageView)
        .invoke("initializeBitmap");
  }

  @Test
  public void callInitializeBitmapOnSetImageURI() throws Exception {
    // Arrange

    // Act
    circleImageView.setImageURI(uri);

    // Assert
    PowerMockito.verifyPrivate(circleImageView)
        .invoke("initializeBitmap");
  }

  @Test
  public void verifySetColorFilter() {
    // Arrange

    // Act
    circleImageView.setColorFilter(colorFilter);

    // Assert
    assertThat(circleImageView.getColorFilter(), is(equalTo(colorFilter)));
  }

}