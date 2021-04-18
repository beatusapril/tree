Object.defineProperty(Array.prototype, "toTree", {
    configurable: false,
    writable: false,
    value: function(getKey, getParentKey) {
        var list = JSON.parse(JSON.stringify(this));
        var root = {};
        for (var index = 0; index < list.length; index++) {
            var parentKey = getParentKey.call(list, list[index], index, list);
            var parent = (list.find(function(item, index) {
                return parentKey === getKey.call(list, item, index, list);
            }) || root);
            (parent.children = parent.children || []).push(list[index]);
        }
        return root.children || [];
    }
});

const locations = [{
    id: 1,
    name: "San Francisco Bay Area",
    parentId: null
}, {
    id: 2,
    name: "San Jose",
    parentId: 3
}, {
    id: 3,
    name: "South Bay",
    parentId: 1
}, {
    id: 4,
    name: "San Francisco",
    parentId: 1
}, {
    id: 5,
    name: "Manhattan",
    parentId: 6
}, ];

function createTreeView(tree, getName) {
    var listDom = document.createElement("ul");
    tree.forEach(function(item) {
        var itemDom = listDom.appendChild(document.createElement("li"));
        if (item.children && item.children.length) {
            var itemName = itemDom.appendChild(document.createElement("span"));
            itemName.textContent = getName.call(item, item);
            itemName.className = "caret";
            var nestedList = itemDom.appendChild(createTreeView(item.children, getName));
            nestedList.className = "nested";
        } else {
            itemDom.textContent = getName.call(item, item);
        }
    });
    return listDom;
}

var tree = locations.toTree(location => location.id, location => location.parentId);
document.body.appendChild(createTreeView(tree, location => location.name)).id = "myUL";