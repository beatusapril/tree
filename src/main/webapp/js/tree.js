let url = window.location.href + '/files';

fetch(url).then(function (response) {
    response.json().then(function (files) {
        let tree = files.toTree(file => file.id, file => file.parentId);
        document.body.appendChild(createTreeView(tree, file => file.name)).id = "myUL";
        addNestedMechanism();
    });
});

Object.defineProperty(Array.prototype, "toTree", {
    configurable: false,
    writable: false,
    value: function (getKey, getParentKey) {
        var list = JSON.parse(JSON.stringify(this));
        var root = {};
        for (var index = 0; index < list.length; index++) {
            var parentKey = getParentKey.call(list, list[index], index, list);
            var parent = (list.find(function (item, index) {
                return parentKey === getKey.call(list, item, index, list);
            }) || root);
            (parent.children = parent.children || []).push(list[index]);
        }
        return root.children || [];
    }
});

function createTreeView(tree, getName) {
    let listDom = document.createElement("ul");
    listDom.className = "treeline";
    console.log("start")
    tree.forEach(function (item) {
        let itemDom = listDom.appendChild(document.createElement("li"));
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

function addNestedMechanism(){
    let toggler = document.getElementsByClassName("caret");
    let i;

    for (i = 0; i < toggler.length; i++) {
        toggler[i].addEventListener("click", function() {
            this.parentElement.querySelector(".nested").classList.toggle("active");
            this.classList.toggle("caret-down");
        });
    }
}