fetch("restservices/movies/search/?" + window.location.search.substr(1)).then(response => response.json()).then(data => {
    let ul = document.createElement("ul");
    for(let movie of data) {
        let li = document.createElement("li");
        let element = document.createElement("a");
        element.href = "restservices/movies/movie?id=" + movie.id;
        element.innerText = movie.title;
        li.appendChild(element);
        ul.appendChild(li);
    }
    document.getElementById("search_results").appendChild(ul);
});