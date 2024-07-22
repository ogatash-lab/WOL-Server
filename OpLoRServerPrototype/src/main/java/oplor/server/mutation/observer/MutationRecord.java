package oplor.server.mutation.observer;

import oplor.server.node.Node;

//DOMの変更に関する情報を保持するためのクラス
public class MutationRecord extends MutationObserver {
    public String type;
    public Node target;
    //public NodeList addedNodes;
    //public NodeList removedNodes;
    public Node previousSibling;
    public String arrtibuteName;
    public String attributeNamespace;
}
