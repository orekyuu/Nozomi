export interface RootState {
  test: string
}

export interface DeploymentState {
  projects: Project[]
}

export interface Project {
  id: string
}
