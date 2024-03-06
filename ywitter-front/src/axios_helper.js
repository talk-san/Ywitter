import axios from 'axios';

axios.defaults.baseURL='http://localhost:8080'
axios.defaults.headers.post["Content-type"] = 'application/json'

export const getAuthToken = () => {
    return window.localStorage.getItem("auth_token");
};

export const setAuthHeader = (token) => {
    if (token !== null) {
      window.localStorage.setItem("auth_token", token);
    } else {
      window.localStorage.removeItem("auth_token");
    }
};

export const isAuthenticated = () => {
    return !!getAuthToken(); 
  };

export const request = (method, url, data) => {
    let headers = {};
    if (getAuthToken() !== null && getAuthToken !== "null") {
        headers = {"Authorization": `Bearer ${getAuthToken()}`};
    }
    
    return axios ( {
        method: method, 
        headers: headers,
        url: url, 
        data: data
    });
}