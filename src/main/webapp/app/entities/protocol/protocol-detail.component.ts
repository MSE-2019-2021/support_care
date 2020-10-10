import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProtocol } from 'app/shared/model/protocol.model';

@Component({
  selector: 'jhi-protocol-detail',
  templateUrl: './protocol-detail.component.html',
})
export class ProtocolDetailComponent implements OnInit {
  protocol: IProtocol | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ protocol }) => (this.protocol = protocol));
  }

  previousState(): void {
    window.history.back();
  }
}
