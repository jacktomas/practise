package com.test;

import junit.framework.TestCase;

public class ServiceDiscoveryTest extends TestCase {

    public void testDiscover() throws Exception {
        ServiceDiscovery serviceDiscovery = new ServiceDiscovery("127.0.0.1:2181");
        String discover = serviceDiscovery.discover();
        System.out.println(discover);

    }
}