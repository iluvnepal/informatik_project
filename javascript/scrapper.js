const siteUrl = "https://remoteok.io/";
const axios = require("axios");
const fetchData = async () => {
  const result = await axios.get(siteUrl);
  return cheerio.load(result.data);
};

console.log(fetchData)