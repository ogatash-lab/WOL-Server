package oplor.server;

import oplor.server.node.Document;
import oplor.server.node.HTMLInputElement;

public class ProcessorImp implements Processor {
    @Override
    public String process(Document document) {
        System.out.println("Document");
        return null;
    }

    public String process(HTMLInputElement htmlInputElement) {
        System.out.println("HTMLInputElement");
        return null;
    }
}
