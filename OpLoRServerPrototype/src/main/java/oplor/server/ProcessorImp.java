package oplor.server;

import oplor.server.node.Document;
import oplor.server.node.HTMLInputElement;

public class ProcessorImp implements Processor {

    //Documentインスタンスを処理
    @Override
    public String process(Document document) {
        System.out.println("Document");
        return null;
    }

    //HTMLInputElementインスタンスを処理
    public String process(HTMLInputElement htmlInputElement) {
        System.out.println("HTMLInputElement");
        return null;
    }
}
