package ru.job4j.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.model.Post;
import ru.job4j.store.Store;
import ru.job4j.store.jdbc.JdbcPostStore;
import ru.job4j.store.memory.MemPostStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JdbcPostStore.class)
public class PostServletTest {

    @Test
    public void whenCreatePost() throws IOException {
        Store<Post> store = MemPostStore.getInstance();

        PowerMockito.mockStatic(JdbcPostStore.class);
        PowerMockito.when(JdbcPostStore.getInstance()).thenReturn(store);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("id")).thenReturn("10");
        when(req.getParameter("name")).thenReturn("name");
        when(req.getParameter("description")).thenReturn("description");

        new PostServlet().doPost(req, resp);

        Post result = store.findById(10);
        assertThat(result.getName(), is("name"));
        assertThat(result.getDescription(), is("description"));
    }

}