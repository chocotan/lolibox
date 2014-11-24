package io.loli.box.startup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.http.util.HttpStatus;

public class LoliBoxStaticHttpHandler extends StaticHttpHandler {
    private static byte[] datas = null;

    private static Logger logger = Logger.getLogger("LoliBoxStaticHttpHandler");

    static {
        InputStream is = ClasspathHelper.class.getResourceAsStream("/404.png");
        if (is == null) {
            logger.warning("没有找到404图片，请将404.png放到classpath中");
        } else {
            try {
                datas = input2byte(is);
            } catch (IOException e) {
                logger.warning("读取404图片时出错:" + e.getMessage());
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
}
