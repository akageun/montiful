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
