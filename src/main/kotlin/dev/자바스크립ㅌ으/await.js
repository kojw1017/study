const timeOut = (f, ms) => new Promise(res => setTimeout(_=> res(f()), ms));
const f1 =_=>"abc";
const f2 =_=>"def";
const start = performance.now();
(async _=>{
    const [v1, v2] = await Promise.all([timeOut(f1, 500), timeOut(f2, 1000)]);
    console.log(v1, v2, performance.now() - start);
})();
