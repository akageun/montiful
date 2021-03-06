$.fn.serializeObject = function () {
    "use strict";
    var result = {};
    $.each(this.serializeArray(), function (i, element) {

        var node = result[element.name];
        if ("undefined" !== typeof node && node !== null) {

            if ($.isArray(node)) {
                node.push(element.value)
            } else {
                result[element.name] = [node, element.value]
            }
        } else {
            result[element.name] = element.value
        }
    });
    return result
};

function logout() {
    //

    $.ajax({
        url: '/user/api/v1/logout',
        type: 'POST',
        async: false,
        dataType: 'json',
        error: function (error) {
            alert("실패");
        },
        success: function (data) {
            location.href = "/login";
        }
    });
}

function isEmpty(obj) {
    for(var prop in obj) {
        if(obj.hasOwnProperty(prop))
            return false;
    }

    return JSON.stringify(obj) === JSON.stringify({});
}


/**
 * Form 내부에 있는 미사용 value 삭제
 *
 * - GET일 경우에만 동작함.
 *
 * @param frm
 */
function removeNotUsedElement(frm) {

    if (frm.method.toUpperCase() === 'GET') {
        for (var i = 0; i < frm.elements.length; i++) {
            var tmpElement = frm.elements[i];
            var tmpVal = tmpElement.value;
            if (tmpVal === undefined || tmpVal === '') {
                tmpElement.disabled = true;
            }
        }
    }
}