package io.loli.box.startup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpStatus;

public class LoliBoxStaticHttpHandler extends StaticHttpHandler {
    private static byte[] datas = null;

    private static Logger LOGGER = Logger.getLogger("LoliBoxStaticHttpHandler");

    static {
        InputStream is = ClasspathHelper.class.getResourceAsStream("/404.png");
        if (is == null) {
            LOGGER.warning("没有找到404图片，请将404.png放到classpath中");
        } else {
            try {
                datas = input2byte(is);
            } catch (IOException e) {
                LOGGER.warning("读取404图片时出错:" + e.getMessage());
            }
        }
    }

    public LoliBoxStaticHttpHandler() {
        super();
        setFileCacheEnabled(true);
    }

    public LoliBoxStaticHttpHandler(String string) {
        super(string);
        setFileCacheEnabled(true);
    }

    protected void onMissingResource(final Request request, final Response response) throws IOException {
        response.setStatus(HttpStatus.NOT_FOUND_404);
        customizedErrorPage(request, response);
    }

    private void customizedErrorPage(final Request request, final Response response) throws IOException {
        if (datas != null) {
            OutputStream os = response.getOutputStream();
            try {
                os.write(datas);
                os.flush();
            } finally {
                IOUtils.closeQuietly(os);
            }

        }
    }

    private static final byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean handle(final String uri, final Request request, final Response response) throws Exception {

        boolean found = false;

        final File[] fileFolders = docRoots.getArray();
        if (fileFolders == null) {
            return false;
        }

        File resource = null;

        for (int i = 0; i < fileFolders.length; i++) {
            final File webDir = fileFolders[i];
            // local file
            resource = new File(webDir, uri);
            final boolean exists = resource.exists();
            final boolean isDirectory = resource.isDirectory();

            if (exists && isDirectory) {
                final File f = new File(resource, "/index.html");
                if (f.exists()) {
                    resource = f;
                    found = true;
                    break;
                }
            }

            if (isDirectory || !exists) {
                found = false;
            } else {
                found = true;
                break;
            }
        }

        if (!found) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, "File not found {0}", resource);
            }
            return false;
        }

        assert resource != null;

        // If it's not HTTP GET - return method is not supported status
        if (!Method.GET.equals(request.getMethod())) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, "File found {0}, but HTTP method {1} is not allowed", new Object[] { resource,
                    request.getMethod() });
            }
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED_405);
            response.setHeader(Header.Allow, "GET");
            return true;
        }

        pickupContentType(response, resource.getPath());

        addToFileCache(request, response, resource);
        response.setHeader(Header.CacheControl, "max-age=86400");
        sendFile(response, resource);
        return true;
    }

}
