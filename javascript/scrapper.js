const siteUrl = "https://understat.com/league/EPL";
const cheerio = require('cheerio');
const request = require('request');
const { StringDecoder } = require('string_decoder');

request({
    method: 'GET',
    url: siteUrl
}, (err, res, body) => {

    if (err) return console.error(err);

    let resp = cheerio.load(body)

    // extract scripts with contents
    let datesScript = resp('script').map((i, x) => x.children[0]).filter((i, x) => x && x.data.match(/datesData/)).get(0);
    let teamsScript = resp('script').map((i, x) => x.children[0]).filter((i, x) => x && x.data.match(/teamsData/)).get(0);

    let datesData = parseData(datesScript);
    let teamsData = parseData(teamsScript);
    console.log(decode_utf8(teamsData));
});

function parseData(response){
    let data = response.data.toString();

    let start = data.indexOf("('") + 2;
    let end = data.indexOf("')");

    var dataOnly = data.substring(start, end);
    console.log(dataOnly);
    return dataOnly;
    // var newData = JSON.parse(dataOnly);
    // var newData = decode_utf8(myString);

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
