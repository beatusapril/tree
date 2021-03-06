let url = window.location.href + '/files';

fetch(url).then(function (response) {
    response.json().then(function (files) {
        let tree = files.toTree(file => file.id, file => file.parentId);
        let element = document.getElementById("container-tree");
        element.appendChild(createTreeView(tree, file => file.name)).id = "myUL";
        addNestedMechanism();
    });
});

Object.defineProperty(Array.prototype, "toTree", {
    configurable: false,
    writable: false,
    value: function (getKey, getParentKey) {
        let list = JSON.parse(JSON.stringify(this));
        let root = {};
        for (let index = 0; index < list.length; index++) {
            let parentKey = getParentKey.call(list, list[index], index, list);
            let parent = (list.find(function (item, index) {
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
    tree.forEach(function (item) {
        let itemDom = listDom.appendChild(document.createElement("li"));
        if (item.children && item.children.length) {
            let itemName = itemDom.appendChild(document.createElement("span"));
            itemName.textContent = item.id + ". " + getName.call(item, item);
            itemName.className = "caret";
            let nestedList = itemDom.appendChild(createTreeView(item.children, getName));
            nestedList.className = "nested";
        } else {
            itemDom.textContent = item.id + ". " + getName.call(item, item);
        }
    });
    return listDom;
}

function addNestedMechanism() {
    let toggler = document.getElementsByClassName("caret");
    let i;

    for (i = 0; i < toggler.length; i++) {
        toggler[i].addEventListener("click", function () {
            this.parentElement.querySelector(".nested").classList.toggle("active");
            this.classList.toggle("caret-down");
        });
    }
}

function addFile() {
    let name = prompt('?????????????? ???????????????? ??????????', '');
    let parentStrId = prompt("?????????????? ?????????? ?????????????????????? ????????????????", 2);
    let parentId = parseInt(parentStrId);
    let recordStrId = prompt("?????????????? ?????????? ???????????? ( ???????????????????? )", '');
    let recordId = parseInt(recordStrId);

    let record = {
        "id": recordId,
        "name": name,
        "parentId": parentId
    }

    fetch(url, {
        method: 'POST',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json'
        },
        redirect: 'follow',
        referrerPolicy: 'no-referrer',
        body: JSON.stringify(record)
    }).then(response => console.log(response));
}

function deleteFile() {
    let numberStr = prompt('?????????????? ?????????? ???????????????????? ??????????', '');
    let number = parseInt(numberStr);
    console.log(number);
    let record = {
        id: number
    };

    fetch(url, {
        method: 'DELETE',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json'
        },
        redirect: 'follow',
        referrerPolicy: 'no-referrer',
        body: JSON.stringify(record)
    }).then(response => console.log(response));
}