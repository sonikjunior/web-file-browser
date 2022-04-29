export const getFolderContent = (path: string) =>
    fetch(
        "http://localhost:8080/folder/content",
        {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({path})
        }).then(handleResponse);

export const unzipArchive = (path: string) =>
    fetch(
        "http://localhost:8080/archive/unzip",
        {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({path})
        }).then(handleResponse);

const handleResponse = (resp: Response) => {
    if (resp.status >= 200 && resp.status <= 299) {
        return resp.json()
    } else {
        return resp.text().then(text => {
            const msg = JSON.parse(text).message;
            alert(msg)
            throw Error(msg)
        });
    }
}
