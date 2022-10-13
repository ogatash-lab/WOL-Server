package oplor.server;


import oplor.server.node.Document;
import oplor.server.node.HTMLInputElement;

public interface Processor {
    String process(Document document);

    String process(HTMLInputElement htmlInputElement);
}
