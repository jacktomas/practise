package protocol.netty.codec;

import org.jboss.marshalling.*;

import java.io.IOException;

/**
 * Created by root on 17-2-16.
 */
public class MarshallingCodeCFactory {
    public static Marshaller buildMarshalling() throws IOException {
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        Marshaller marshaller = factory.createMarshaller(configuration);
        return marshaller;

    }

    public static Unmarshaller buildUnMarshalling() throws IOException {
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        Unmarshaller unmarshaller = factory.createUnmarshaller(configuration);
        return unmarshaller;
    }
}
