package api.iterator.backend.utils;

import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Utility class for HttpServlet
 */
public class HttpServletUtils {
    public static void writeJsonResponse(HttpServletResponse response, String responseBody) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(responseBody);
        response.getWriter().flush();
    }
}
