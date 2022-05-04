export interface Props {
    files: FileCacheProps[]
}

export interface ChildProps {
    file: FileCacheProps
    updateParentState: Function
}

export interface FileProps {
    path: string
    name: string
    type: string
}

export interface FileCacheProps extends FileProps {
    content?: FileCacheProps[] | null
}
