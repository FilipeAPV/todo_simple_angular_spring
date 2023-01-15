export class Task {
  public id: number;
  public title: string;
  public content: string;
  public done: boolean;

  constructor(id: number, title:string, content:string, done:boolean) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.done = done;
  }
}
