
export default function authHeader() {
  let user = JSON.parse(sessionStorage.getItem('user')); //returns JSON string, parsed into js object

  if (user && user.accessToken) { //if user exists
    // for Spring Boot back-end
    return { Authorization: 'Bearer ' + user.accessToken }; //Uses Bearer Token format
  } else {
    return "No user or token found";
  }
}