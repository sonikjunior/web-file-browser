import doc from "../img/file-lines-regular.svg";
import file from "../img/file-regular.svg";
import folder from "../img/folder-regular.svg";
import picture from "../img/image-regular.svg";
import archive from "../img/file-zipper-regular.svg";

export const resolveType = (type: string) => {
    switch (type) {
        case 'folder': return folder
        case 'archive': return archive
        case 'document': return doc
        case 'picture': return picture
        default: return file
    }
}
