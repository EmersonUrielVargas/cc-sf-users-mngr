const { getUserById } = require('./dynamoBdClient');

async function getUser(id) {
    const rs = await getUserById(id);
    console.log(rs)
    return rs;
}

module.exports = {
    getUser
};