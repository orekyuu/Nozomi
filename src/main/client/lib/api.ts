import axios from "axios"
import { Project } from "@/lib/types"

export class ApiClient {
  getProjects(): Promise<Project> {
    return axios.get("/api/projects").then(response => {
      return response.data as Project
    })
  }
}
