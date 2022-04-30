export interface Props {
    files: FileProps[]
}

export interface ChildProps {
    file: FileProps
    setListState: Function
}

export interface FileProps {
    path: string
    name: string
    type: string
}

export interface FileCacheProps extends FileProps {
    content?: FileCacheProps[]
}
