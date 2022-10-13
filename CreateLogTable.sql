BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "CompositionEvent" (
	"compositioneventID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"data"	TEXT,
	"locate"	TEXT
);
CREATE TABLE IF NOT EXISTS "Document" (
	"documentID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"characterSet"	TEXT,
	"compatMode"	TEXT,
	"contentType"	TEXT,
	"doctype"	TEXT,
	"documentURI"	TEXT,
	"hidden"	TEXT,
	"selectedStyleSheetSet"	TEXT,
	"visibilityState"	TEXT,
	"cookies"	TEXT,
	"dir"	TEXT,
	"designMode"	TEXT,
	"domain"	TEXT,
	"lastModified"	TEXT,
	"location"	TEXT,
	"readyState"	TEXT,
	"referrer"	TEXT,
	"title"	TEXT,
	"URL"	TEXT
);
CREATE TABLE IF NOT EXISTS "Documenttype" (
	"documenttypeID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"name"	TEXT,
	"publicId"	TEXT,
	"systemId"	TEXT
);
CREATE TABLE IF NOT EXISTS "Element" (
	"elementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"className"	TEXT,
	"clientHeight"	TEXT,
	"clientLeft"	TEXT,
	"clientTop"	TEXT,
	"computedName"	TEXT,
	"computedRole"	TEXT,
	"id"	TEXT,
	"innerHTML"	TEXT,
	"localName"	TEXT,
	"namespaceURI"	TEXT,
	"outerHTML"	TEXT,
	"prefix"	TEXT,
	"scrollHeight"	TEXT,
	"scrollWidth"	TEXT,
	"slot"	TEXT,
	"tagName"	TEXT,
	"undoScope"	TEXT
);
CREATE TABLE IF NOT EXISTS "Event" (
	"eventID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"bubbles"	TEXT,
	"cancelable"	TEXT,
	"composed"	TEXT,
	"defaultPrevented"	TEXT,
	"eventPhase"	TEXT,
	"timeStamp"	TEXT,
	"type"	TEXT,
	"isTrusted"	TEXT,
	"absTime"	TEXT
);
CREATE TABLE IF NOT EXISTS "FocusEvent" (
	"focuseventID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLAnchorElement" (
	"htmlanchorelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"accessKey"	TEXT,
	"download"	TEXT,
	"hash"	TEXT,
	"host"	TEXT,
	"hostname"	TEXT,
	"href"	TEXT,
	"hreflang"	TEXT,
	"media"	TEXT,
	"password"	TEXT,
	"origin"	TEXT,
	"pathname"	TEXT,
	"port"	TEXT,
	"protocol"	TEXT,
	"refferrerPolicy"	TEXT,
	"rel"	TEXT,
	"search"	TEXT,
	"tabindex"	TEXT,
	"target"	TEXT,
	"text"	TEXT,
	"type"	TEXT,
	"username"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLAreaElement" (
	"htmlareaelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"accessKey"	TEXT,
	"alt"	TEXT,
	"coords"	TEXT,
	"download"	TEXT,
	"hash"	TEXT,
	"host"	TEXT,
	"hostname"	TEXT,
	"media"	TEXT,
	"password"	TEXT,
	"origin"	TEXT,
	"pathname"	TEXT,
	"port"	TEXT,
	"protocol"	TEXT,
	"refferrerPolicy"	TEXT,
	"rel"	TEXT,
	"search"	TEXT,
	"shape"	TEXT,
	"tabindex"	TEXT,
	"target"	TEXT,
	"type"	TEXT,
	"username"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLBaseElement" (
	"htmlbaseelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"href"	TEXT,
	"target"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLButtonElement" (
	"htmlbuttonelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"accessKey"	TEXT,
	"autofocus"	TEXT,
	"disabled"	TEXT,
	"formAcion"	TEXT,
	"formEnctype"	TEXT,
	"formMethod"	TEXT,
	"firnBiValidate"	TEXT,
	"formTarget"	TEXT,
	"name"	TEXT,
	"tabIndex"	TEXT,
	"type"	TEXT,
	"validationMessage"	TEXT,
	"value"	TEXT,
	"willValidate"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLCanvasElement" (
	"htmlcanvaselementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"height"	TEXT,
	"width"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLDataElement" (
	"htmldataelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"value"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLDialogElement" (
	"htmldialogelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"open"	TEXT,
	"returnValue"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLElement" (
	"htmlelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"accessKey"	TEXT,
	"accessKeyLabel"	TEXT,
	"contentEditable"	TEXT,
	"isContentEditable"	TEXT,
	"draggable"	TEXT,
	"hidden"	TEXT,
	"itemScope"	TEXT,
	"itemId"	TEXT,
	"lang"	TEXT,
	"offsetHeight"	TEXT,
	"offsetLeft"	TEXT,
	"offsetTop"	TEXT,
	"offsetWidth"	TEXT,
	"spellcheck"	TEXT,
	"style"	TEXT,
	"tabIndex"	TEXT,
	"title"	TEXT,
	"translate"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLEmbedElement" (
	"htmlembedelement"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"height"	TEXT,
	"src"	TEXT,
	"type"	TEXT,
	"width"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLFieldElement" (
	"htmlfieldelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"disabled"	TEXT,
	"element"	TEXT,
	"name"	TEXT,
	"type"	TEXT,
	"validationMessage"	TEXT,
	"willValidate"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLFormElement" (
	"htmlformelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"length"	TEXT,
	"name"	TEXT,
	"method"	TEXT,
	"target"	TEXT,
	"action"	TEXT,
	"encoding"	TEXT,
	"enctype"	TEXT,
	"acceptCharset"	TEXT,
	"autocomplete"	TEXT,
	"noValidate"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLInputElement" (
	"htmlinputelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"formAction"	TEXT,
	"formEnctype"	TEXT,
	"formMethod"	TEXT,
	"formNoValidate"	TEXT,
	"formTarget"	TEXT,
	"name"	TEXT,
	"type"	TEXT,
	"disabled"	TEXT,
	"autofocus"	TEXT,
	"required"	TEXT,
	"value"	TEXT,
	"validationMessage"	TEXT,
	"willValidate"	TEXT,
	"checked"	TEXT,
	"defaultChecked"	TEXT,
	"indeterminate"	TEXT,
	"alt"	TEXT,
	"height"	TEXT,
	"src"	TEXT,
	"width"	TEXT,
	"accept"	TEXT,
	"autocomplete"	TEXT,
	"maxLength"	TEXT,
	"size"	TEXT,
	"pattern"	TEXT,
	"placeholder"	TEXT,
	"readyOnly"	TEXT,
	"min"	TEXT,
	"max"	TEXT,
	"selectionStart"	TEXT,
	"selectionEnd"	TEXT,
	"selectionDirection"	TEXT,
	"defaultValue"	TEXT,
	"dirName"	TEXT,
	"multiple"	TEXT,
	"step"	TEXT,
	"valueAsNumber"	TEXT,
	"autocapitalize"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLLIElement" (
	"htmllielementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"value"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLLabelElement" (
	"htmllabelelementIF"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"accessKey"	TEXT,
	"htmlFor"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLLegendElement" (
	"htmllegendelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"accessKey"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLLinkElement" (
	"htmllinkelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"as"	TEXT,
	"crossOrigin"	TEXT,
	"disabled"	TEXT,
	"href"	TEXT,
	"hrefkang"	TEXT,
	"media"	TEXT,
	"referrerPolicy"	TEXT,
	"rel"	TEXT,
	"type"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLMetaElement" (
	"htmlmetaelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"content"	TEXT,
	"httpEquiv"	TEXT,
	"name"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLMeterElement" (
	"htmlmeterelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"high"	TEXT,
	"low"	TEXT,
	"max"	TEXT,
	"min"	TEXT,
	"optimum"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLModelElement" (
	"htmlmodelelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"cite"	TEXT,
	"detetime"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLOListElement" (
	"htmlolistelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"reversed"	TEXT,
	"start"	TEXT,
	"type"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLObjectElement" (
	"htmlobjectelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"data"	TEXT,
	"height"	TEXT,
	"name"	TEXT,
	"tabindex"	TEXT,
	"typeMustMatch"	TEXT,
	"useMap"	TEXT,
	"validationMessage"	TEXT,
	"width"	TEXT,
	"willValidate"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLOptGroupElement" (
	"htmloptelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"disabled"	TEXT,
	"label"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLOptionElement" (
	"htmloptionelementID"	integer,
	"ref"	TEXT,
	"defaultSelected"	TEXT,
	"disabled"	TEXT,
	"index"	TEXT,
	"label"	TEXT,
	"selected"	TEXT,
	"text"	TEXT,
	"value"	TEXT,
	PRIMARY KEY("htmloptionelementID")
);
CREATE TABLE IF NOT EXISTS "HTMLOutputElement" (
	"htmloutputelementID"	integer,
	"ref"	TEXT,
	"defaultValue"	TEXT,
	"name"	TEXT,
	"type"	TEXT,
	"validationMessage"	TEXT,
	"value"	TEXT,
	"willValidate"	TEXT,
	PRIMARY KEY("htmloutputelementID")
);
CREATE TABLE IF NOT EXISTS "HTMLParamElement" (
	"htmlparamelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"height"	TEXT,
	"width"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLProgressElement" (
	"htmlprogresselementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"max"	TEXT,
	"position"	TEXT,
	"value"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLQuoteElement" (
	"htmlquoteelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"cite"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLScriptElement" (
	"htmlscriptelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"type"	TEXT,
	"src"	TEXT,
	"charset"	TEXT,
	"async"	TEXT,
	"defer"	TEXT,
	"crossOrigin"	TEXT,
	"text"	TEXT,
	"noModule"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLSelectElement" (
	"htmlselectelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"autofocus"	TEXT,
	"disabled"	TEXT,
	"length"	TEXT,
	"multiple"	TEXT,
	"name"	TEXT,
	"required"	TEXT,
	"selectedIndex"	TEXT,
	"size"	TEXT,
	"type"	TEXT,
	"validationMessage"	TEXT,
	"value"	TEXT,
	"willValidate"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLSlotElement" (
	"htmlslotelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"name"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLSourceElement" (
	"htmlsourceelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"keySystem"	TEXT,
	"media"	TEXT,
	"sizes"	TEXT,
	"src"	TEXT,
	"srcset"	TEXT,
	"type"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLStyleElement" (
	"htmlstyleelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"keySystem"	TEXT,
	"media"	TEXT,
	"sizes"	TEXT,
	"src"	TEXT,
	"srcset"	TEXT,
	"type"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLTableCellElement" (
	"htmltablecellelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"addr"	TEXT,
	"cellIndex"	TEXT,
	"colSpan"	TEXT,
	"rowSpan"	TEXT,
	"scope"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLTableColElement" (
	"htmltablecolelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"span"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLTableElement" (
	"htmltableelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"sortable"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLTableRowElement" (
	"htmltablerowelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"rowIndex"	TEXT,
	"sectionRowIndex"	TEXT
);
CREATE TABLE IF NOT EXISTS "HTMLTitleElement" (
	"htmltitleelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	text
);
CREATE TABLE IF NOT EXISTS "HTMLTrackElement" (
	"htmltrackelementID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"kind"	TEXT,
	"src"	TEXT,
	"srclang"	TEXT,
	"label"	TEXT,
	"m_default"	TEXT,
	"readyState"	TEXT
);
CREATE TABLE IF NOT EXISTS "InputEvent" (
	"inputeventID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"data"	TEXT,
	"inputType"	TEXT,
	"isComposing"	TEXT
);
CREATE TABLE IF NOT EXISTS "KeyboardEvent" (
	"keyboardeventID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"altKey"	TEXT,
	"code"	REAL,
	"ctrlKey"	TEXT,
	"isComposing"	TEXT,
	"key2"	TEXT,
	"locate"	TEXT,
	"location"	TEXT,
	"metaKey"	TEXT,
	"repeat2"	TEXT,
	"shiftKey"	TEXT
);
CREATE TABLE IF NOT EXISTS "Log" (
	"logID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"userID"	TEXT,
	"EventType"	TEXT,
	"NodeType"	TEXT,
	"absTime"	TEXT
);
CREATE TABLE IF NOT EXISTS "MouseEvent" (
	"mouseeventID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"altKey"	TEXT,
	"button"	TEXT,
	"buttons"	TEXT,
	"client_X"	TEXT,
	"client_Y"	TEXT,
	"ctrlKey"	TEXT,
	"metaKey"	TEXT,
	"movementX"	TEXT,
	"movementY"	TEXT,
	"offsetX"	TEXT,
	"offsetY"	TEXT,
	"pageX"	TEXT,
	"pageY"	TEXT,
	"screenX"	TEXT,
	"screenY"	TEXT,
	"shiftKey"	TEXT,
	"x"	TEXT,
	"y"	TEXT
);
CREATE TABLE IF NOT EXISTS "Node" (
	"nodeID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"baseURI"	TEXT,
	"innerText"	TEXT,
	"nodeName"	TEXT,
	"nodeValue"	TEXT,
	"textContent"	TEXT
);
CREATE TABLE IF NOT EXISTS "UIEvent" (
	"uieventID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"detail"	TEXT
);
CREATE TABLE IF NOT EXISTS "WheelEvent" (
	"wheeleventID"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ref"	TEXT,
	"deltaX"	TEXT,
	"deltaY"	TEXT,
	"deltaZ"	TEXT,
	"dataMode"	TEXT
);
CREATE VIEW TaskCheck as select ref, baseURI, innerText from Node where baseURI=="https://spica.gakumu.tuat.ac.jp/syllabus/DetailMain.aspx";
CREATE VIEW TASKCHECKER as select ref, baseURI, innerText from Node;
CREATE VIEW innerText as select ref, innerText from Node;
CREATE VIEW ElementSub as select Element.ref, Element.namespaceURI from Element;
CREATE VIEW LogNode as select * from Log left outer join Node on Node.ref==Log.logID;
CREATE VIEW user1TaskCheck as select distinct Node.baseURI from Log left outer join Node on Log.logID==Node.ref where Log.userID=='69e0a2c5-90ba-417c-b184-fe924345d069';
CREATE VIEW user21Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="e2976350-7943-43d9-9ea8-8ef612151d27" AND Event.type=='click';
CREATE VIEW user20Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="8544e157-028b-49c6-9f83-fe8b477b712d" AND Event.type=='click';
CREATE VIEW user19Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="de413c9a-a102-4685-9598-7ee46ff8cbce" AND Event.type=='click';
CREATE VIEW user18Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="62a9a461-87fe-40a7-acfc-5796547b44ae" AND Event.type=='click';
CREATE VIEW user17Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="a2d6bec0-7046-46ba-9d91-ef42f6ad9ae7" AND Event.type=='click';
CREATE VIEW checkID as select distinct Log.userID from Log;
CREATE VIEW user16Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="b4bf0b02-c54e-4298-b4f3-fe8765f61795" AND Event.type=='click';
CREATE VIEW user15Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="e2a732fd-3edc-43a4-a94d-859d3982e5c9" AND Event.type=='click';
CREATE VIEW user14Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="29650801-104c-42e3-882f-60965582fd5d" AND Event.type=='click';
CREATE VIEW user13Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="de85bef7-1df8-4007-96b8-81a05873271d" AND Event.type=='click';
CREATE VIEW user12Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="125659e0-2f24-4a46-8738-26d0aacfefc4" AND Event.type=='click';
CREATE VIEW user10Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="9a5f1f79-1080-42d3-a273-b968302aadec" AND Event.type=='click';
CREATE VIEW user9Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID== "125659e0-2f24-4a46-8738-26d0aacfefc4" AND Event.type=='click';
CREATE VIEW user8Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="5a283cd6-6f68-4651-bb63-cea308e1d0a5" AND Event.type=='click';
CREATE VIEW user7Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="bbcfba73-e5ea-47d4-8db4-6ee17d965bca" AND Event.type=='click';
CREATE VIEW user6Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="29650801-104c-42e3-882f-60965582fd5d" AND Event.type=='click';
CREATE VIEW user5Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="bf7e6e61-2a8f-40f5-b375-80f4e844a3c9" AND Event.type=='click';
CREATE VIEW user4Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=="ba2b0c35-cefb-4c1e-b7d4-649aa3972a11" AND Event.type=='click';
CREATE VIEW user3Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=='e4a933c3-346b-425f-839d-42f2ca10e6e3' AND Event.type=='click';
CREATE VIEW user2Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=='a261f25e-c26b-4b41-a560-74979d9163c3' AND Event.type=='click';
CREATE VIEW user1Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=='69e0a2c5-90ba-417c-b184-fe924345d069' AND Event.type=='click';
CREATE VIEW user11Time as select distinct Log.logID, Log.userID, Event.type, Node.baseURI, Log.absTime from (Log left outer join Event on Log.logID==Event.ref) left outer join Node on Log.logID==Node.ref where Log.userID=='6034fc57-8b56-4420-a1db-8d1e5ce3e2c7' AND Event.type=='click';
CREATE VIEW test as select Log.absTime from Log left outer join Event on Event.ref=Log.logID;
COMMIT;
