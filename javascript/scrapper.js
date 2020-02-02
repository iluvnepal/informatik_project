const siteUrl = "https://understat.com/league/EPL";
const cheerio = require('cheerio');
const request = require('request');
const { StringDecoder } = require('string_decoder');
const jsesc = require('jsesc');

request({
    method: 'GET',
    url: siteUrl
}, (err, res, body) => {

    if (err) return console.error(err);

    let resp = cheerio.load(body)

    // extract scripts with contents
    let datesScript = resp('script').map((i, x) => x.children[0]).filter((i, x) => x && x.data.match(/datesData/)).map((i, x) => x.data).get(0);
    let teamsScript = resp('script').map((i, x) => x.children[0]).filter((i, x) => x && x.data.match(/teamsData/)).map((i, x) => x.data).get(0);

    let allScripts = resp("script");
    let scriptsWithChildren = resp('script').map((i, x) => x.children[0]).filter((i, x) => x);

    let datesData = parseData(datesScript);
    let teamsData = parseData(teamsScript);
    console.log(typeof datesScript.data);
    console.log(decode_utf8(teamsData));
});

function parseData(data){
    let start = data.indexOf("('") + 2;
    let end = data.indexOf("')");

    let myString = "\x7B\x2271\x22\x3A\x7B\x22id\x22\x3A\x2271\x22,\x22title\x22\x3A\x22Aston\x20Villa\x22,\x22history\x22";
    var dataOnly = data.substring(start, end);
    var newData = decode_utf8(dataOnly);
    console.log(newData);
    return dataOnly;
    // console.log(newData);
}

function encode_utf8(s) {
    return unescape(encodeURIComponent(s));
}

function decode_utf8(s) {
    return decodeURIComponent(escape(s));
}

//string_json_obj = ''
//    for element in scripts:
//        if variable_name in element.text:
//            string_json_obj = element.text.strip()
//
//    start_index = string_json_obj.index("('") + 2
//    end_index = string_json_obj.index("')")
//
//    data_json = string_json_obj[start_index:end_index]
//    data_json = data_json.encode('utf8').decode('unicode-escape')
//
//    data = json.loads(data_json)
//
//    return data
