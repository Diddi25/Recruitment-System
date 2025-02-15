const springPath = "http://localhost:8080/";

async function login(user, pass) {
    const path = "login"
    const options = {
        method: "POST",
        headers: {'Content-Type': 'application/json',},
        body: JSON.stringify({username: user, password: pass}),
    };
    let result = await fetch(springPath + path, options).then(response => response.json())
    return result;
}

export {login}