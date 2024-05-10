package com.mygdx.game.tests;

import com.mygdx.game.Objects.ActivityImage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class ActivityImageTest {
    private ActivityImage mockedImage = mock(ActivityImage.class, withSettings()
            .useConstructor("Activitys/cs.png")
            .defaultAnswer(CALLS_REAL_METHODS));
    @Test
    public void test(){}
}
