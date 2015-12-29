exports.add = function () {

    var sum = 0,
        i = 0,
        args = arguments,
        l = args.length;
    for (i = 0; i < l; i++) {
        sum += args[i];
    }
    return sum;
}
