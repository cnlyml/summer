/*
 * Copyright 2016 Cnlyml
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.lyml.summer.test;

import me.lyml.summer.groovy.init.HandlerInitMessage;
import me.lyml.summer.test.spring.JettyFactory;
import me.lyml.summer.test.spring.Profiles;
import org.eclipse.jetty.server.Server;

/**
 * @ClassName: StartServer
 * @author: cnlyml
 * @date: 2016/8/30 9:23
 */
public class StartServer {
    private static final int PORT = 8080;
    private static final String CONTEXT = "/";
    public static final String[] TLD_JAR_NAMES = new String[] { "sitemesh", "spring-webmvc", "shiro-web"};

    public static void main(String[] args) {

        Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);

        Server server = JettyFactory.createServerInSource(PORT, CONTEXT);
        JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);

        try {
            HandlerInitMessage.handlerMessage();
            server.start();

            System.out.println("[INFO] Server running at http://localhost:" + PORT + CONTEXT);
            System.out.println("[HINT] Hit Enter to reload the application quickly");
        } catch (Throwable e) {
            /*e.printStackTrace();*/
            System.err.println("----------------------------------------------------------");
            System.err.println("Server启动失败");
            System.err.println("----------------------------------------------------------");
            System.exit(-1);
        }

    }
}
