
var strippedText = "\\x7B\\x2271\\x22\\x3A\\x7B\\x22id";
console.log((strippedText));


function encode_utf8(s) {
    return unescape(encodeURIComponent(s));
}

function decode_utf8(s) {
    return decodeURIComponent(escape(s));
}