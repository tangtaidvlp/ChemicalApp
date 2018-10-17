package phamf.com.chemicalapp.CustomView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import phamf.com.chemicalapp.Adapter.ViewPager_Lesson_Adapter;
import phamf.com.chemicalapp.R;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;
import static phamf.com.chemicalapp.CustomView.LessonViewCreator.BOLD_TEXT;
import static phamf.com.chemicalapp.CustomView.LessonViewCreator.PART_DEVIDER;
import static phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.*;

@RunWith(PowerMockRunner.class)
public class LessonViewCreatorTest {

    @Test
    public void testAddViewFromContent () {

        String soft_content = BIG_TITLE + BOLD_TEXT + "Here is Big title" + COMPONENT_DEVIDER
                            + SMALL_TITLE + BOLD_TEXT + "Here is Small Title" + COMPONENT_DEVIDER
                            + CONTENT + BOLD_TEXT + "Here is Content" + COMPONENT_DEVIDER
                + PART_DEVIDER
                            + BIG_TITLE + BOLD_TEXT  + "Here is Big title2" + COMPONENT_DEVIDER
                            + SMALL_TITLE + BOLD_TEXT + "Here is Small Title2" + COMPONENT_DEVIDER
                            + CONTENT + BOLD_TEXT + "Here is Content2" + COMPONENT_DEVIDER
                + PART_DEVIDER
                            + BIG_TITLE + BOLD_TEXT + "Here is Big title3" + COMPONENT_DEVIDER
                            + SMALL_TITLE + BOLD_TEXT + "Here is Small Title3" + COMPONENT_DEVIDER
                            + CONTENT + BOLD_TEXT + "Here is Content3" + COMPONENT_DEVIDER;


        Context context = mock(Context.class);

        LinearLayout linear_Layout = mock(LinearLayout.class);

        LessonViewCreator lessonViewCreator = new LessonViewCreator(mock(ViewPager_Lesson_Adapter.class));

        LessonViewCreator.ViewCreator view_creator = spy(new LessonViewCreator.ViewCreator(context, linear_Layout));

        lessonViewCreator.separatePart_And_BindDataToViewPG(soft_content);

        when(context.getDrawable(R.drawable.back_icon)).thenReturn(mock(Drawable.class));

        doAnswer(invocation -> null).when(view_creator).addImageContent(R.drawable.back_icon,0,0,0,0,0, 0);

        doAnswer(invocation -> null).when(view_creator).addBigTitle(any(String.class), any(String.class));

        doAnswer(invocation -> null).when(view_creator).addSmallTitle(any(String.class), any(String.class));

        doAnswer(invocation -> null).when(view_creator).addContent(any(String.class), any(String.class));

        view_creator.addView(soft_content);
        verify(view_creator, times(3)).addBigTitle(any(String.class), any(String.class));
        verify(view_creator, times(3)).addContent(any(String.class), any(String.class));
        verify(view_creator, times(3)).addSmallTitle(any(String.class), any(String.class));
        verify(view_creator, never()).addImageContent(any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class),any(Integer.class) );
    }


}