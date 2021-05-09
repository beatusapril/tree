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

async function postData(url = '', data = {}) {
    const response = await fetch(url, {
        method: 'POST',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json',
            //'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: JSON.stringify(data)
    });
    return await response.json();
}

function addFile() {
    let name = prompt('Введите название файла', "");
    let parentId = prompt("Введите номер предыдущего элемента", '');
    let recordId = prompt("Введите номер записи ( уникальный )", '');

    const record = {
        "name": name,
        "parentId": parentId,
        "recordId": recordId
    }
    postData('https://example.com/answer', record)
        .then((data) => {
            console.log(data); // JSON data parsed by `response.json()` call
        });
}

function deleteFile() {
    let number = prompt('Введите номер удаляемого файла', "");

    const deleteMethod = {
        method: 'DELETE', // Method itself
        headers: {
            'Content-type': 'application/json; charset=UTF-8' // Indicates the content
        },
        body: JSON.stringify({"recordId": number}) // We send data in JSON format
    }

    fetch(url, deleteMethod)
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(err => console.log(err))
}